package com.github.mingyu.fooddeliveryapi.domain.delivery.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryStatusMessage {
    private Long orderId;
    private String status;
}
