package com.github.mingyu.fooddeliveryapi.domain.order.application.dto;


import com.github.mingyu.fooddeliveryapi.domain.order.domain.OrderStatus;
import com.github.mingyu.fooddeliveryapi.domain.order.domain.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderParam implements OrderCommand {
    private String orderId;
    private Long userId;
    private String storeId;
    private String storeName;
    private String storePhone;
    private String storeAddress;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    private String requests;
    private List<OrderItemParam> items;
    private Integer totalPrice;
}
