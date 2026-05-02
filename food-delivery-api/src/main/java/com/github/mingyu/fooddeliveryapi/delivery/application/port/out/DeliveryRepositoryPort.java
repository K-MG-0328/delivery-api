package com.github.mingyu.fooddeliveryapi.delivery.application.port.out;

import com.github.mingyu.fooddeliveryapi.delivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepositoryPort extends JpaRepository<Delivery, Long> {
    List<Delivery> getDeliveryByOrderId(String orderId);
}
