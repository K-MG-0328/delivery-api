package com.github.mingyu.fooddeliveryapi.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.event.dto.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    @Qualifier("stringKafkaTemplate")
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendOrderPaidEvent(OrderPaidEvent event){
        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order-paid", payload);

            log.info("Kafka 주문 결제 완료 이벤트 전송됨: {}", payload);
        }catch (JsonProcessingException e){
            throw new RuntimeException("주문 이벤트 실패", e);
        }
    }
}
