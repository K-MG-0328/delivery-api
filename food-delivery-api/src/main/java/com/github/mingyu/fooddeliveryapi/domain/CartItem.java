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
public class CartItem {

    private Long menuId;
    private List<Long> menuOptionIds;
    private int quantity;
    private int price;
}
