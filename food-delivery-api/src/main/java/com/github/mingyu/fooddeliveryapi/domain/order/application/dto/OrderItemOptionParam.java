package com.github.mingyu.fooddeliveryapi.domain.order.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemOptionParam {
    private String itemId;
    private String optionName;
    private Integer price;
}
