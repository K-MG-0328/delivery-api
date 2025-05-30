package com.github.mingyu.fooddeliveryapi.domain.cart.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartItemParam {
    private String cartId;
    private String itemId;
    private String menuId;
    private String name;
    private Integer price;
    private List<CartItemOptionParam> options;
    private Integer quantity;
}
