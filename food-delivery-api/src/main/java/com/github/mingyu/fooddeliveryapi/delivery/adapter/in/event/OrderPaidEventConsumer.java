package com.github.mingyu.fooddeliveryapi.delivery.adapter.in.event;

import com.github.mingyu.fooddeliveryapi.delivery.application.service.DeliveryService;
import com.github.mingyu.fooddeliveryapi.order.domain.event.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaidEventConsumer {

    private final DeliveryService deliveryService;

    @KafkaListener(topics = "order-paid", groupId = "delivery-start-service", containerFactory = "orderPaidKafkaListenerContainerFactory")
    public void listen(OrderPaidEvent event) {
        deliveryService.startDelivery(event);
    }
}
