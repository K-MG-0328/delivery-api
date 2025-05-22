package com.github.mingyu.fooddeliveryapi.event.dto;

import com.github.mingyu.fooddeliveryapi.entity.Cart;
import com.github.mingyu.fooddeliveryapi.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartEvent {
    private Cart cart;
    private List<CartItem> cartItems;
    private String timestamp;
}
