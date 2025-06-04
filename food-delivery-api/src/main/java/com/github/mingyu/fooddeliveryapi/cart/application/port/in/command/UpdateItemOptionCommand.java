package com.github.mingyu.fooddeliveryapi.cart.application.port.in.command;

import com.github.mingyu.fooddeliveryapi.cart.domain.vo.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateItemOptionCommand {
    private final String userId;
    private final Item item;
}
