package com.github.mingyu.fooddeliveryapi.cart.domain;

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
