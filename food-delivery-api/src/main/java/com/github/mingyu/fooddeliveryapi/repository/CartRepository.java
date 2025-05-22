package com.github.mingyu.fooddeliveryapi.repository;

import com.github.mingyu.fooddeliveryapi.entity.Cart;
import com.github.mingyu.fooddeliveryapi.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {

    List<Cart> findByUserIdAndStatus(Long userId, CartStatus status);
}
