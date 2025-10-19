package com.email.writer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class EmailGeneratorService {

    private static final Logger log = LogManager.getLogger(EmailGeneratorService.class);
    private final WebClient webClient;

    @Value("${gemini.api.url:#{null}}")
    private String geminiApiUrl;

    @Value("${gemini.api.key:#{null}}")
    private String geminiApiKey;


    public EmailGeneratorService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String generateEmailReply(EmailRequest emailRequest) {
        try {
            String prompt = buildPrompt(emailRequest);

            // Build request body in Gemini 2.5 Flash format
            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "parts", List.of(
                                            Map.of("text", prompt)
                                    )
                            )
                    )
            );

            System.out.println("Request JSON: " + new ObjectMapper().writeValueAsString(requestBody));

            String targetUrl = geminiApiUrl + "?key=" + geminiApiKey;

            String response = webClient.post()
                    .uri(targetUrl)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return extractResponseContent(response);

        } catch (Exception e) {
            log.error("Error generating email reply", e);
            return "Error: " + e.getMessage();
        }
    }


    private String extractResponseContent(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            JsonNode candidates = root.path("candidates");
            if (!candidates.isArray() || candidates.size() == 0) {
                return "No candidates returned by Gemini API";
            }

            JsonNode content = candidates.get(0).path("content");
            JsonNode parts = content.path("parts");
            if (!parts.isArray() || parts.size() == 0) {
                return "No content parts returned by Gemini API";
            }

            return parts.get(0).path("text").asText();

        } catch (Exception e) {
            return "Error parsing response: " + e.getMessage();
        }
    }



    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("""
        You are an AI assistant that writes professional, human-like email replies.

        Task:
        Generate a complete, well-structured email reply to the following email content.

        Guidelines:
        - Maintain the context of the original email.
        - Use the specified tone (if provided) for consistency.
        - Be concise, polite, and natural.
        - Include appropriate greetings, acknowledgements, and closings.
        - If the sender asked a question or made a request, address it directly.
        - If the tone is 'formal', use professional phrasing.
        - If the tone is 'friendly', use approachable and conversational phrasing.
        - Do not repeat the original email content.
        """);

        // Add the original email content
        prompt.append("Original Email:\n")
                .append(emailRequest.getEmailContent())
                .append("\n\nNow write the best possible email reply in the given tone.");

        return prompt.toString();
    }

}
