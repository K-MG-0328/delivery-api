package com.github.mingyu.fooddeliveryapi.cart.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemOption {
    private final String itemId;
    private final String optionName;
    private final Integer price;
}
