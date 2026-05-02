package com.github.mingyu.fooddeliveryapi.domain.delivery.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> getDeliveryByOrderId(Long orderId);
}
