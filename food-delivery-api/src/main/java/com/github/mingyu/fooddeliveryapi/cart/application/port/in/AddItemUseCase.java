package com.github.mingyu.fooddeliveryapi.cart.application.port.in;

import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.AddItemCommand;

public interface AddItemUseCase {
    void addToCartItem(AddItemCommand command);
}
