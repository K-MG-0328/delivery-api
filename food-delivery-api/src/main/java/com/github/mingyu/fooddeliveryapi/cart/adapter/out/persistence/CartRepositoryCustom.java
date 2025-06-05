package com.github.mingyu.fooddeliveryapi.cart.adapter.out.persistence;

import com.github.mingyu.fooddeliveryapi.cart.domain.Cart;
import com.github.mingyu.fooddeliveryapi.cart.domain.CartStatus;

import java.util.List;

public interface CartRepositoryCustom {
    List<Cart> findByUserIdAndInStatus(String userId, List<CartStatus> status);
    long deleteCart(Cart cart);
}
