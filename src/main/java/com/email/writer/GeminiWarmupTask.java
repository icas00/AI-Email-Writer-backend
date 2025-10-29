package com.email.writer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Component
public class GeminiWarmupTask {

    @Value("${GEMINI_KEY:#{null}}")
    private String geminiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 300000) // every 5 minutes
    public void warmUpGemini() {
        if (geminiApiKey == null || geminiApiKey.isBlank()) {
            System.out.println("Gemini API key not set; skipping warm-up.");
            return;
        }

        try {
            String geminiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"
                    + "?key=" + geminiApiKey;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = "{\"contents\":[{\"parts\":[{\"text\":\"ping\"}]}]}";
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(geminiUrl, entity, String.class);
            System.out.println(" Warm-up ping sent successfully. Response: " + response.getStatusCode());

        } catch (Exception e) {
            System.err.println("Warm-up ping failed: " + e.getMessage());
        }
    }
}
