package com.dosemate.Dosemate.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OlamaService {

    private final RestTemplate restTemplate;

    public OlamaService() {
        this.restTemplate = new RestTemplate();
    }

    public String getCompletion(String prompt) {
        // Local Ollama URL
        String url = "http://localhost:11434/v1/completions";

        // Append instruction to make paragraph correct and grammatical
        String enhancedPrompt = prompt + "\n\nPlease write a complete, clear patient history based on the description given . Use proper sentence structure and polish it for readability.I want only in 3 lines alone I repeat I want Only in Three Lines alone";

        // Prepare request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-r1:1.5b"); // your local model name
        requestBody.put("prompt", enhancedPrompt);
        requestBody.put("max_tokens", 1000);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("ollama"); // default key for local Ollama

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                if (body.containsKey("choices")) {
                    var choices = (java.util.List<Map<String, Object>>) body.get("choices");
                    if (!choices.isEmpty()) {
                        return (String) choices.get(0).get("text");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error contacting Ollama: " + e.getMessage();
        }

        return "No response from Ollama";
    }
}

