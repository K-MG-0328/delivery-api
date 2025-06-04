package com.github.mingyu.fooddeliveryapi.cart.application.port.in;

import com.github.mingyu.fooddeliveryapi.cart.adapter.in.web.response.CartResponse;

public interface SearchCartUseCase {
    CartResponse searchCart(String userId);
}
