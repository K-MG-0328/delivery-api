package com.github.mingyu.fooddeliveryapi.repository;

import com.github.mingyu.fooddeliveryapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
