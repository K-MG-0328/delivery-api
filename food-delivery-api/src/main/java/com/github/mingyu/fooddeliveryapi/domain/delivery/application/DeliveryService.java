package com.github.mingyu.fooddeliveryapi.domain.delivery.application;

import com.github.mingyu.fooddeliveryapi.domain.delivery.domain.DeliveryState;
import com.github.mingyu.fooddeliveryapi.domain.delivery.event.DeliveryStatusMessage;
import com.github.mingyu.fooddeliveryapi.domain.delivery.domain.Delivery;
import com.github.mingyu.fooddeliveryapi.domain.order.event.OrderPaidEvent;
import com.github.mingyu.fooddeliveryapi.domain.delivery.infrastructure.producer.DeliveryStatusEventProducer;
import com.github.mingyu.fooddeliveryapi.domain.delivery.domain.DeliveryRepository;
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

    private final DeliveryRepository deliveryRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final DeliveryStatusEventProducer deliveryStatusEventProducer;

    public void startDelivery(OrderPaidEvent event) {

        // 배송 생성
        Delivery delivery = new Delivery();
        delivery.setOrderId(event.getOrderId());
        delivery.setUserId(event.getUserId());
        delivery.setStoreId(event.getStoreId());
        delivery.setStatus(DeliveryState.STARTED);
        delivery.setStartedDate(LocalDateTime.now());

        deliveryRepository.save(delivery);

        String key = "delivery:status:" + event.getOrderId();
        redisTemplate.opsForValue().set(key, DeliveryState.STARTED.toString(), Duration.ofHours(2));

        DeliveryStatusMessage message = new DeliveryStatusMessage(event.getOrderId(), DeliveryState.STARTED.toString());
        deliveryStatusEventProducer.sendDeliveryStatusEvent(message);
    }

    public void sendStatusUpdate(Long orderId, String status) {
        String key = "delivery:status:" + orderId;
        redisTemplate.opsForValue().set(key, status, Duration.ofHours(2));

        DeliveryStatusMessage message = new DeliveryStatusMessage(orderId, status);
        deliveryStatusEventProducer.sendDeliveryStatusEvent(message);
    }

    public void completeDelivery(Long orderId) {
        // 배송 완료
        List<Delivery> deliverys = deliveryRepository.getDeliveryByOrderId(orderId);
        Delivery delivery = deliverys.get(0);
        delivery.setStatus(DeliveryState.DELIVERED);
        delivery.setCompletedDate(LocalDateTime.now());

        deliveryRepository.save(delivery);

        String key = "delivery:status:" + orderId;
        redisTemplate.opsForValue().set(key, DeliveryState.DELIVERED.toString(), Duration.ofHours(2));

        DeliveryStatusMessage message = new DeliveryStatusMessage(orderId, DeliveryState.DELIVERED.toString());
        deliveryStatusEventProducer.sendDeliveryStatusEvent(message);
    }

    public void cancelDelivery(Long orderId) {

        List<Delivery> deliverys = deliveryRepository.getDeliveryByOrderId(orderId);
        Delivery delivery = deliverys.get(0);
        delivery.setStatus(DeliveryState.CANCELED);
        delivery.setCompletedDate(LocalDateTime.now());

        deliveryRepository.save(delivery);

        String key = "delivery:status:" + orderId;
        redisTemplate.opsForValue().set(key, DeliveryState.CANCELED.toString(), Duration.ofHours(2));

        DeliveryStatusMessage message = new DeliveryStatusMessage(orderId, DeliveryState.CANCELED.toString());
        deliveryStatusEventProducer.sendDeliveryStatusEvent(message);
    }
}
