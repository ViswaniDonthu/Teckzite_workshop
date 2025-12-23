//package com.example.teckzite.service;
//
//import com.example.teckzite.entity.Techie;
//import com.example.teckzite.repository.TechieRepository;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class TechieAnalysisService {
//
//    private final TechieRepository repository;
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @Value("${openai.api.key}")
//    private String apiKey;
//
//    @Value("${openai.model}")
//    private String model;
//
//    public TechieAnalysisService(TechieRepository repository) {
//        this.repository = repository;
//    }
//
//    public String analyzeTechie(Long id) {
//
//        // 1️⃣ Fetch techie
//        Techie techie = repository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Techie not found with id: " + id));
//
//        // 2️⃣ Prepare prompt
//        String prompt = """
//                A student has the following skills and interests.
//
//                Skills: %s
//                Interests: %s
//
//                Based on this, suggest:
//                1. Future skills to learn
//                2. Career direction
//                3. Technologies to focus on
//
//                Keep it concise and practical.
//                """.formatted(techie.getSkills(), techie.getInterests());
//
//        // 3️⃣ OpenAI request body
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("model", model);
//        requestBody.put("messages", List.of(
//                Map.of("role", "user", "content", prompt)
//        ));
//        requestBody.put("temperature", 0.7);
//
//        // 4️⃣ Headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(apiKey);
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//
//        // 5️⃣ Call OpenAI
//        ResponseEntity<Map> response = restTemplate.exchange(
//                "https://api.openai.com/v1/chat/completions",
//                HttpMethod.POST,
//                entity,
//                Map.class
//        );
//
//        // 6️⃣ Extract response text
//        List<Map<String, Object>> choices =
//                (List<Map<String, Object>>) response.getBody().get("choices");
//
//        Map<String, Object> message =
//                (Map<String, Object>) choices.get(0).get("message");
//
//        return message.get("content").toString();
//    }
//}
