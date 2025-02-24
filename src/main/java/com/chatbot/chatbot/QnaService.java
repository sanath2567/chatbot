package com.chatbot.chatbot;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.PreparedStatement;
import java.util.Map;

@Service
public class QnaService {
  @Value("${gemini.api.url}")
  private String geminiApiUrl;
  @Value("${gemini.api.key}")
  private String geminiApiKey;
   private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
   public QnaService(WebClient.Builder webClientBuilder) {
       this.webClient=webClientBuilder.build();
   }

    String getAnswer(String question) {
        Map<String,Object> requesBody=Map.of(
                "contents",new Object[]{
                        Map.of("parts",new Object[]{
                                Map.of("text",question)
                        })
                }
        );
     String response=webClient.post()
                 .uri(geminiApiUrl+geminiApiKey)
                 .header("Content-Type", "application/json")
                 .header(HttpHeaders.CONTENT_TYPE, "application/json")
                 .bodyValue(requesBody)
                 .retrieve()
                 .bodyToMono(String.class)
                 .block();
         // parsing and processing the response from Gemini API
        return response;
    }
    private String extractAnswer(String jsonResponse) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            return root.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {
            return "Error extracting response: " + e.getMessage();
        }
    }


}
