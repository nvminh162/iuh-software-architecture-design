package com.nvminh162.mq.service;

import com.nvminh162.mq.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    public void sendMessage(Message message) {
        // Đảm bảo message có ID và timestamp
        if (message.getId() == null) {
            message.setId(UUID.randomUUID().toString());
        }
        if (message.getTimestamp() == null) {
            message.setTimestamp(java.time.LocalDateTime.now());
        }

        log.info("Gửi tin nhắn: {}", message);

        CompletableFuture<SendResult<String, Message>> future = kafkaTemplate.send(topicName, message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Gửi tin nhắn thành công với offset=[{}]", result.getRecordMetadata().offset());
            } else {
                log.error("Không thể gửi tin nhắn=[{}] do lỗi: {}", message, ex.getMessage());
            }
        });
    }

    public void sendMessage(String content, String sender) {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setContent(content);
        message.setSender(sender);
        message.setTimestamp(java.time.LocalDateTime.now());
        sendMessage(message);
    }
}
