package com.github.mingyu.fooddeliveryapi.cart.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Embeddable
@AllArgsConstructor
public class CartItemOption {

    protected CartItemOption() {}

    private String itemId;
    private String optionName;
    private Integer price;
}
