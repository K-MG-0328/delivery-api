package com.github.mingyu.fooddeliveryapi.cart.application.port.in.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateQuantityCommand {
    private final String userId;
    private final String itemId;
    private final Integer quantity;
}
