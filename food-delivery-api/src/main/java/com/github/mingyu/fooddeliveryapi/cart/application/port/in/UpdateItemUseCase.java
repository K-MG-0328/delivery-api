package com.github.mingyu.fooddeliveryapi.cart.application.port.in;

import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.UpdateItemOptionCommand;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.UpdateQuantityCommand;

public interface UpdateItemUseCase {
    void updateItemOption(UpdateItemOptionCommand command);
    void updateItemQuantity(UpdateQuantityCommand command) ;
}
