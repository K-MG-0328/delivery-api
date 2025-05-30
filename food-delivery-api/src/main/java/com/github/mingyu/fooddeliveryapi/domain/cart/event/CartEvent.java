package com.github.mingyu.fooddeliveryapi.domain.cart.event;

import com.github.mingyu.fooddeliveryapi.domain.cart.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartEvent {
    private Cart cart;
    private String timestamp;
}
