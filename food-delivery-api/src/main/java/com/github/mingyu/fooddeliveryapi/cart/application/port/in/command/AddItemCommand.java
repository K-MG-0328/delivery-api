package com.github.mingyu.fooddeliveryapi.cart.application.port.in.command;

import com.github.mingyu.fooddeliveryapi.cart.domain.vo.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddItemCommand{
    private String userId;
    private String storeId;
    private Item item;
}
