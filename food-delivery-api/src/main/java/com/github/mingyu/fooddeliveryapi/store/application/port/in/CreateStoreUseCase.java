package com.github.mingyu.fooddeliveryapi.store.application.port.in;

import com.github.mingyu.fooddeliveryapi.store.application.port.in.command.StoreCreateCommand;

public interface CreateStoreUseCase {
    void createStore(StoreCreateCommand command);
}
