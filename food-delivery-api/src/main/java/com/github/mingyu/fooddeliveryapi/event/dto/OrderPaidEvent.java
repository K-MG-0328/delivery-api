package com.github.mingyu.fooddeliveryapi.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaidEvent {
    private Long orderId;
    private Long userId;
    private Long storeId;
    private int totalPrice;
    private String paidDate; //결제 시간
    private String deliveryAddress;
}
