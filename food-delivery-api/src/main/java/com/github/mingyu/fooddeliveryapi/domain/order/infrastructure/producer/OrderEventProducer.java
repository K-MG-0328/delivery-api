package com.github.mingyu.fooddeliveryapi.domain.order.infrastructure.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.domain.order.event.OrderPaidEvent;
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

    /* 결제 완료 후 메시지 발행 */
    public void sendOrderPaidEvent(OrderPaidEvent event){
        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order-paid", event.getUserId().toString(), payload);

            log.info("Kafka 주문 결제 완료 이벤트 전송됨: {}", payload);
        }catch (JsonProcessingException e){
            throw new RuntimeException("주문 이벤트 실패", e);
        }
    }
}
