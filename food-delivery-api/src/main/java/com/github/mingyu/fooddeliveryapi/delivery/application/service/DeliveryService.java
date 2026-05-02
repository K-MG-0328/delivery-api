package com.github.mingyu.fooddeliveryapi.delivery.application.service;

import com.github.mingyu.fooddeliveryapi.delivery.domain.DeliveryState;
import com.github.mingyu.common.event.DeliveryStatusMessage;
import com.github.mingyu.fooddeliveryapi.delivery.domain.Delivery;
import com.github.mingyu.fooddeliveryapi.order.domain.Order;
import com.github.mingyu.fooddeliveryapi.order.domain.event.OrderPaidEvent;
import com.github.mingyu.fooddeliveryapi.delivery.adapter.out.event.DeliveryStatusEventProducer;
import com.github.mingyu.fooddeliveryapi.delivery.application.port.out.DeliveryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/*
 * 고객 관점 - 서버가 고객에게 라이더의 실시간 정보를 push
 * 라이더 관점 - 서버에게 실시간 위치 pulling
 * */

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepositoryPort deliveryRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final DeliveryStatusEventProducer deliveryStatusEventProducer;

    public void startDelivery(OrderPaidEvent event) {
        Order order = event.getOrder();

        Delivery delivery = new Delivery();
        delivery.setOrderId(order.getOrderId());
        delivery.setUserId(order.getUserId());
        delivery.setStoreId(order.getStoreInfo().getStoreId());
        delivery.setStatus(DeliveryState.STARTED);
        delivery.setStartedDate(LocalDateTime.now());

        deliveryRepository.save(delivery);

        String key = "delivery:status:" + order.getOrderId();
        redisTemplate.opsForValue().set(key, DeliveryState.STARTED.toString(), Duration.ofHours(2));

        DeliveryStatusMessage message = new DeliveryStatusMessage(order.getOrderId(), DeliveryState.STARTED.toString());
        deliveryStatusEventProducer.sendDeliveryStatusEvent(message);
    }

    public void sendStatusUpdate(String orderId, String status) {
        String key = "delivery:status:" + orderId;
        redisTemplate.opsForValue().set(key, status, Duration.ofHours(2));

        DeliveryStatusMessage message = new DeliveryStatusMessage(orderId, status);
        deliveryStatusEventProducer.sendDeliveryStatusEvent(message);
    }

    public void completeDelivery(String orderId) {
        List<Delivery> deliveries = deliveryRepository.getDeliveryByOrderId(orderId);
        Delivery delivery = deliveries.get(0);
        delivery.setStatus(DeliveryState.DELIVERED);
        delivery.setCompletedDate(LocalDateTime.now());

        deliveryRepository.save(delivery);

        String key = "delivery:status:" + orderId;
        redisTemplate.opsForValue().set(key, DeliveryState.DELIVERED.toString(), Duration.ofHours(2));

        DeliveryStatusMessage message = new DeliveryStatusMessage(orderId, DeliveryState.DELIVERED.toString());
        deliveryStatusEventProducer.sendDeliveryStatusEvent(message);
    }

    public void cancelDelivery(String orderId) {
        List<Delivery> deliveries = deliveryRepository.getDeliveryByOrderId(orderId);
        Delivery delivery = deliveries.get(0);
        delivery.setStatus(DeliveryState.CANCELED);
        delivery.setCompletedDate(LocalDateTime.now());

        deliveryRepository.save(delivery);

        String key = "delivery:status:" + orderId;
        redisTemplate.opsForValue().set(key, DeliveryState.CANCELED.toString(), Duration.ofHours(2));

        DeliveryStatusMessage message = new DeliveryStatusMessage(orderId, DeliveryState.CANCELED.toString());
        deliveryStatusEventProducer.sendDeliveryStatusEvent(message);
    }
}
