package com.github.mingyu.fooddeliveryapi.domain.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUser_UserId(Long userId);
}
