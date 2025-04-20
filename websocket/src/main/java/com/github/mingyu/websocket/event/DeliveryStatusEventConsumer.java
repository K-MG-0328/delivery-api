package com.github.mingyu.websocket.event;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.websocket.dto.DeliveryStatusMessage;
import com.github.mingyu.websocket.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryStatusEventConsumer {
    private final ObjectMapper objectMapper;
    private final DeliveryService deliveryService;

    @KafkaListener(topics = "delivery-status", groupId = "delivery-update-service")
    public void listen(ConsumerRecord<String, String> record) {

        try {
            String value = record.value();
            DeliveryStatusMessage message = objectMapper.readValue(value, DeliveryStatusMessage.class);

            deliveryService.sendStatusUpdate(message);
        } catch (Exception e) {
            log.error("배송 생성 실패", e);
        }
    }
}
