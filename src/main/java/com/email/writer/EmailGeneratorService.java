package com.email.writer;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

            // Build request body in Gemini 1.5 Flash format
            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "parts", List.of(
                                            Map.of("text", prompt)
                                    )
                            )
                    )
            );

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
    You are writing an email reply as a real human professional, NOT as an AI assistant.

    CRITICAL: Write like a genuine person - natural, conversational, and authentic.
    AVOID: Overly formal phrases, robotic patterns, AI-like politeness, or corporate jargon.

    Task:
    Generate a complete, human-sounding email reply to the following email.

    Guidelines:
    - Write as if YOU personally are replying, not an AI writing on someone's behalf.
    - Use natural language people actually use in real emails.
    - Be direct and genuine - avoid flowery or overly polite language.
    - Include appropriate greetings and closings that feel authentic.
    - If the sender asked a question, answer it naturally and clearly.
    - Keep it conversational - imagine you're talking to a colleague or friend (depending on tone).
    - Do NOT use phrases like "I hope this email finds you well" or "Thank you for reaching out".
    - Avoid repetitive pleasantries or unnecessary formality.
    - Write in active voice with simple, clear sentences.

    Tone Guidelines:
    - Friendly: Warm and casual, like chatting with a colleague you like. Use contractions (I'm, don't, etc).
    - Professional: Respectful but not stiff. Clear and business-like without being robotic.
    - Concise: Get straight to the point. No fluff, just the essential message.
    - Apologetic: Genuinely sorry, empathetic, and taking responsibility. Sound human, not corporate.
    - Grateful: Authentic appreciation without being over-the-top. Mean it.
    - Brief: 1-3 sentences max. Quick, direct, natural.

    HUMANIZATION RULES:
    NEVER use: "I hope this message finds you well", "Thank you for reaching out", "Please do not hesitate"
    NEVER sound like: Customer service bot, corporate template, or AI assistant
    DO sound like: A real person typing a quick, genuine email
    DO use: Contractions, simple words, natural flow, personality

    """);

        // Add tone specification
        if (emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
            prompt.append("Required Tone: ").append(emailRequest.getTone()).append("\n\n");
        }

        // Add the original email content
        prompt.append("Original Email:\n")
                .append(emailRequest.getEmailContent())
                .append("\n\n");

        prompt.append("Now write your reply using the '")
                .append(emailRequest.getTone() != null ? emailRequest.getTone() : "Friendly")
                .append("' tone.\n\n");

        prompt.append("REMEMBER: Write like a REAL HUMAN, not an AI. Be natural, authentic, and conversational. Avoid AI-sounding phrases at all costs.");

        return prompt.toString();
    }

}
