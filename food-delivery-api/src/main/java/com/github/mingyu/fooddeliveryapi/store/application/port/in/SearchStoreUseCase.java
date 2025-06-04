package com.github.mingyu.fooddeliveryapi.store.application.port.in;

import com.github.mingyu.fooddeliveryapi.store.adapter.in.web.response.StoreListResponse;
import com.github.mingyu.fooddeliveryapi.store.adapter.in.web.response.StoreResponse;
import com.github.mingyu.fooddeliveryapi.store.application.port.in.command.StoreSearchCommand;

public interface SearchStoreUseCase {
    StoreResponse searchStore(String storeId);
    StoreListResponse searchStores(StoreSearchCommand command);
}
