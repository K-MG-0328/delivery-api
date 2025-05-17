package com.github.mingyu.fooddeliveryapi.event.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.event.dto.OrderPaidEvent;
import com.github.mingyu.fooddeliveryapi.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaidEventConsumer {

    private final ObjectMapper objectMapper;
    private final DeliveryService deliveryService;

    @KafkaListener(topics = "order-paid", groupId = "delivery-start-service")
    public void listen(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            OrderPaidEvent event = objectMapper.readValue(message, OrderPaidEvent.class);

            deliveryService.startDelivery(event);
        } catch (Exception e) {
            log.error("배송 생성 실패", e);
        }
    }
}
