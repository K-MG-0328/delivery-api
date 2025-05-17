package com.github.mingyu.fooddeliveryapi.repository;

import com.github.mingyu.fooddeliveryapi.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
