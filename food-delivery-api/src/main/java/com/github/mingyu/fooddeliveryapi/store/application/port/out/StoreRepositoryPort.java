package com.github.mingyu.fooddeliveryapi.store.application.port.out;

import com.github.mingyu.fooddeliveryapi.store.domain.Store;

import java.util.List;

public interface StoreRepositoryPort{
    void save(Store store);

    List<Store> findByNameAndCategory(String name, String category, String deliveryAreas);

    Store findById(String storeId);
}
