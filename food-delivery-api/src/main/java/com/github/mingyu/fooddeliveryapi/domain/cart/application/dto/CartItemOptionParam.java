package com.github.mingyu.fooddeliveryapi.domain.cart.application.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Embeddable
@AllArgsConstructor
public class CartItemOptionParam {

    protected CartItemOptionParam() {}

    private String itemId;
    private String optionName;
    private Integer price;
}
