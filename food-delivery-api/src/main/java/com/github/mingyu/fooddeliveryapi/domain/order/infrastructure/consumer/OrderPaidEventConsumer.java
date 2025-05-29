package com.github.mingyu.fooddeliveryapi.domain.order.infrastructure.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.domain.order.event.OrderPaidEvent;
import com.github.mingyu.fooddeliveryapi.domain.delivery.application.DeliveryService;
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
