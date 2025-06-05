package com.github.mingyu.fooddeliveryapi.cart.application.port.out;

import com.github.mingyu.fooddeliveryapi.cart.domain.Cart;
import com.github.mingyu.fooddeliveryapi.cart.domain.CartStatus;

import java.util.List;

public interface CartRepositoryPort {
    void deleteCart(Cart cart);

    List<Cart> findByUserIdAndStatus(String userId, CartStatus cartStatus);
}
