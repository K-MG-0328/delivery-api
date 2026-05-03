package com.github.mingyu.fooddeliveryapi.order.application.port.out;

import com.github.mingyu.fooddeliveryapi.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepositoryPort extends JpaRepository<Order, String> {
    List<Order> findByUserId(String userId);
}
