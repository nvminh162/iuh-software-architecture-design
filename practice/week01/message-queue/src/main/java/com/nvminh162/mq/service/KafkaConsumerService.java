package com.nvminh162.mq.service;

import com.nvminh162.mq.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(
            @Payload Message message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {
        
        log.info("═══════════════════════════════════════════════════════");
        log.info("Nhận được tin nhắn từ Kafka:");
        log.info("Topic: {}", topic);
        log.info("Partition: {}", partition);
        log.info("Offset: {}", offset);
        log.info("Message ID: {}", message.getId());
        log.info("Sender: {}", message.getSender());
        log.info("Content: {}", message.getContent());
        log.info("Timestamp: {}", message.getTimestamp());
        log.info("═══════════════════════════════════════════════════════");

        // Xử lý tin nhắn ở đây (ví dụ: lưu vào database, gửi email, etc.)
        
        // Acknowledge message để đánh dấu đã xử lý xong
        if (acknowledgment != null) {
            acknowledgment.acknowledge();
        }
    }
}
