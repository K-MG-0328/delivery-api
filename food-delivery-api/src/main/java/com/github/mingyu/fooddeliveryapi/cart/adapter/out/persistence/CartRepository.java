package com.github.mingyu.fooddeliveryapi.cart.adapter.out.persistence;

import com.github.mingyu.fooddeliveryapi.cart.domain.Cart;
import com.github.mingyu.fooddeliveryapi.cart.domain.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, String>, CartRepositoryCustom {

    List<Cart> findByUserIdAndStatus(String userId, CartStatus status);
}
