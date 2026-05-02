package com.github.mingyu.fooddeliveryapi.domain.order.event;

import com.github.mingyu.fooddeliveryapi.domain.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaidEvent {
    private Order order;
    private String paidDate; //결제 시간
}
