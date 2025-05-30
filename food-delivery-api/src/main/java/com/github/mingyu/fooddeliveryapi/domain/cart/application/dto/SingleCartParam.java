package com.github.mingyu.fooddeliveryapi.domain.cart.application.dto;

import com.github.mingyu.fooddeliveryapi.domain.cart.domain.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SingleCartParam implements CartCommand{
    private String cartId;
    private Long userId;
    private String storeId;
    private CartStatus status;
    private CartItemParam item;
    private Integer totalPrice;
}
