package com.github.mingyu.fooddeliveryapi.cart.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Item {
    private final String itemId;
    private final String menuId;
    private final String name;
    private final Integer price;
    private final List<ItemOption> options;
    private final Integer quantity;
}
