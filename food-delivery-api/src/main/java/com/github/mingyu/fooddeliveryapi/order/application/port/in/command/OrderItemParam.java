package com.github.mingyu.fooddeliveryapi.order.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderItemParam {
    private String orderId;
    private String itemId;
    private String menuId;
    private String name;
    private Integer price;
    private List<OrderItemOptionParam> options;
    private Integer quantity;
}
