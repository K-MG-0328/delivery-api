package com.github.mingyu.fooddeliveryapi.domain.cart.application.dto;


import com.github.mingyu.fooddeliveryapi.domain.cart.domain.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartParam  implements CartCommand {
    private String cartId;
    private Long userId;
    private String storeId;
    private CartStatus status;
    private List<CartItemParam> items;
    private Integer totalPrice;

    public void addItem(CartItemParam item) {
        items.add(item);
    }
}
