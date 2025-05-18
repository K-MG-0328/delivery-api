package com.github.mingyu.fooddeliveryapi.event.dto;

import com.github.mingyu.fooddeliveryapi.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartEvent {
    private String userId;
    private OrderStatus status;
    private String itemId; // menuId
    private String timestamp;
    private Long storeId;
}
