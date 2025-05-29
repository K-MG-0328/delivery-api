package com.github.mingyu.fooddeliveryapi.domain.cart.domain;

import java.util.List;

public interface CartRepositoryCustom {
    List<Cart> findByUserIdAndInStatus(Long userId, List<CartStatus> status);
}
