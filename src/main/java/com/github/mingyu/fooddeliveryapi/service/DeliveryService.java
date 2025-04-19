package com.github.mingyu.fooddeliveryapi.service;

import com.github.mingyu.fooddeliveryapi.event.dto.DeliveryStatusMessage;
import com.github.mingyu.fooddeliveryapi.entity.Delivery;
import com.github.mingyu.fooddeliveryapi.enums.DeliveryStatus;
import com.github.mingyu.fooddeliveryapi.event.dto.OrderPaidEvent;
import com.github.mingyu.fooddeliveryapi.event.producer.DeliveryStatusEventProducer;
import com.github.mingyu.fooddeliveryapi.repository.DeliveryRepository;
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
        delivery.setStatus(DeliveryStatus.STARTED);
        delivery.setStartedDate(LocalDateTime.now());

        deliveryRepository.save(delivery);

        String key = "delivery:status:" + event.getOrderId();
        redisTemplate.opsForValue().set(key, DeliveryStatus.STARTED.toString(), Duration.ofHours(2));

        DeliveryStatusMessage message = new DeliveryStatusMessage(event.getOrderId(), DeliveryStatus.STARTED.toString());
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
        delivery.setStatus(DeliveryStatus.DELIVERED);
        delivery.setCompletedDate(LocalDateTime.now());

        deliveryRepository.save(delivery);

        String key = "delivery:status:" + orderId;
        redisTemplate.opsForValue().set(key, DeliveryStatus.DELIVERED.toString(), Duration.ofHours(2));

        DeliveryStatusMessage message = new DeliveryStatusMessage(orderId, DeliveryStatus.DELIVERED.toString());
        deliveryStatusEventProducer.sendDeliveryStatusEvent(message);
    }

    public void cancelDelivery(Long orderId) {

        List<Delivery> deliverys = deliveryRepository.getDeliveryByOrderId(orderId);
        Delivery delivery = deliverys.get(0);
        delivery.setStatus(DeliveryStatus.CANCELED);
        delivery.setCompletedDate(LocalDateTime.now());

        deliveryRepository.save(delivery);

        String key = "delivery:status:" + orderId;
        redisTemplate.opsForValue().set(key, DeliveryStatus.CANCELED.toString(), Duration.ofHours(2));

        DeliveryStatusMessage message = new DeliveryStatusMessage(orderId, DeliveryStatus.CANCELED.toString());
        deliveryStatusEventProducer.sendDeliveryStatusEvent(message);
    }
}
