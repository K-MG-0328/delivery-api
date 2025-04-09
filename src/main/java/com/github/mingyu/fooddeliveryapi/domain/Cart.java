package com.github.mingyu.fooddeliveryapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Long userId;
    private Long storeId;
    private List<CartItem> items;

    public int getTotalPrice() {
        return items.stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();
    }
}