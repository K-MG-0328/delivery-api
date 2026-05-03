package com.github.mingyu.fooddeliveryapi.order.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemOptionParam {
    private String itemId;
    private String optionName;
    private Integer price;
}
