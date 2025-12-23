package com.example.teckzite.service;

import com.example.teckzite.entity.Techie;
import com.example.teckzite.repository.TechieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TechieService {

    private final TechieRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    public TechieService(TechieRepository repository) {
        this.repository = repository;
    }

    public Techie registerTechie(Techie techie) {
        return repository.save(techie);
    }

    public List<Techie> getAllTechies() {
        return repository.findAll();
    }
    public Techie getTechieById(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            return new TechieNotFoundException("Techie not found with id: " + id);
        });
    }


    public List<Techie> getTechiesByCollege(String college) {
        List<Techie> techies = repository.findByCollege(college);
        if (techies.isEmpty()) {
            throw new TechieNotFoundException("No techies found for college: " + college);
        }
        return techies;
    }

    public void deleteTechie(Long id) {
        repository.deleteById(id);
    }


    public String analyzeTechie(Long id) {

        Techie techie = getTechieById(id);

        String prompt = """
                A student has the following skills and interests.

                Skills: %s
                Interests: %s

                Based on this, suggest:
                1. Future skills to learn
                2. Career direction
                3. Technologies to focus on

                Keep it concise and practical.
                """.formatted(techie.getSkills(), techie.getInterests());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));
        requestBody.put("temperature", 0.7);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                Map.class
        );

        List<Map<String, Object>> choices =
                (List<Map<String, Object>>) response.getBody().get("choices");

        Map<String, Object> message =
                (Map<String, Object>) choices.get(0).get("message");

        return message.get("content").toString();
    }

    //sample request body :
    //  {
    //     "model": "${openai.model}",
    //     "messages": [
    //       {
    //         "role": "user",
    //         "content": "A student has the following skills and interests.\n\nSkills: {techie.getSkills()}\nInterests: {techie.getInterests()}\n\nBased on this, suggest:\n1. Future skills to learn\n2. Career direction\n3. Technologies to focus on\n\nKeep it concise and practical."
    //       }
    //     ],
    //     "temperature": 0.7
    //   }


    // sample response body :
    // {
    //     "id": "chatcmpl-abc123...",
    //     "object": "chat.completion",
    //     "created": 1677858242,
    //     "model": "gpt-3.5-turbo-0301",
    //     "choices": [
    //       {
    //         "index": 0,
    //         "message": {
    //           "role": "assistant",
    //           "content": "Based on the student's skills in Java and web development with interests in AI and machine learning, here are my suggestions:\n\n1. Future skills to learn: Python, TensorFlow/PyTorch, cloud computing (AWS/Azure)\n2. Career direction: Machine Learning Engineer or AI Developer\n3. Technologies to focus on: Python ecosystem, deep learning frameworks, cloud platforms"
    //         },
    //         "finish_reason": "stop"
    //       }
    //     ],
    //     "usage": {
    //       "prompt_tokens": 85,
    //       "completion_tokens": 92,
    //       "total_tokens": 177
    //     }
    //   }
}
