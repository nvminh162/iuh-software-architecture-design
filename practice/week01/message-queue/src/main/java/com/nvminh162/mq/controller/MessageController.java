package com.nvminh162.mq.controller;

import com.nvminh162.mq.model.Message;
import com.nvminh162.mq.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody Message message) {
        kafkaProducerService.sendMessage(message);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Tin nhắn đã được gửi đến Kafka");
        response.put("data", message);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-simple")
    public ResponseEntity<Map<String, Object>> sendSimpleMessage(
            @RequestParam String content,
            @RequestParam String sender) {
        
        kafkaProducerService.sendMessage(content, sender);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Tin nhắn đã được gửi đến Kafka");
        response.put("content", content);
        response.put("sender", sender);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("service", "Kafka Message Queue Demo");
        return ResponseEntity.ok(response);
    }
}
