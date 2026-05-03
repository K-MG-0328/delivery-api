package com.github.mingyu.common.event;

public record DeliveryStatusMessage(String orderId, String status) {
}
