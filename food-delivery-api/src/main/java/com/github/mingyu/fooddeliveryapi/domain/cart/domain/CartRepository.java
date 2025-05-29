package com.github.mingyu.fooddeliveryapi.domain.cart.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {

    List<Cart> findByUserIdAndStatus(Long userId, CartStatus status);
}
