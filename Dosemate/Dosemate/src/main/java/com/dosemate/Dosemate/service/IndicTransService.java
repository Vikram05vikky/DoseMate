package com.dosemate.Dosemate.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IndicTransService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String translate(String text, String targetLanguage) {

        String url = "http://localhost:5000/translate";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("source_language", "english");
        requestBody.put("target_language", targetLanguage.toLowerCase());
        requestBody.put("text", text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK &&
                    response.getBody() != null) {

                return response.getBody().get("translated_text").toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🔒 Safety fallback
        return text;
    }
}
