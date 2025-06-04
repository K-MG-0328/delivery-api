package com.github.mingyu.fooddeliveryapi.store.application.port.in;

import com.github.mingyu.fooddeliveryapi.store.application.port.in.command.StoreUpdateCommand;

public interface UpdateStoreUseCase {
    void updateStore(StoreUpdateCommand command);
}
