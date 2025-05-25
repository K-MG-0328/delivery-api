package com.github.mingyu.fooddeliveryapi.repository;

import com.github.mingyu.fooddeliveryapi.entity.Cart;
import com.github.mingyu.fooddeliveryapi.enums.CartStatus;

import java.util.List;

public interface CartRepositoryCustom {
    List<Cart> findByUserIdAndInStatus(Long userId, List<CartStatus> status);
}
