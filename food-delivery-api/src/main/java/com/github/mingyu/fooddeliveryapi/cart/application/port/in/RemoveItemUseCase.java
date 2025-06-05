package com.github.mingyu.fooddeliveryapi.cart.application.port.in;

import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.RemoveItemCommand;

public interface RemoveItemUseCase {
    void removeItem(RemoveItemCommand command);
    void clearCart(String userId);
}
