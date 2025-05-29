package com.github.mingyu.fooddeliveryapi.domain.cart.event;

import com.github.mingyu.fooddeliveryapi.domain.cart.domain.Cart;
import com.github.mingyu.fooddeliveryapi.domain.cart.domain.CartItem;
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
