package com.github.mingyu.fooddeliveryapi.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.event.dto.DeliveryStatusMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryStatusEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendDeliveryStatusEvent(DeliveryStatusMessage event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("delivery-status", payload);
        }catch (JsonProcessingException e){
            throw new RuntimeException("배송 상태 이벤트 실패", e);
        }

    }
}
