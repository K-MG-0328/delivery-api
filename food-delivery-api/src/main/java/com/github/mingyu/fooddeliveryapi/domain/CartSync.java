package com.github.mingyu.fooddeliveryapi.domain;

import com.github.mingyu.fooddeliveryapi.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartSync {

    private Cart cart;
    private boolean dbSync;
}
