package com.github.mingyu.fooddeliveryapi.store.adapter.out.persistence;

import com.github.mingyu.fooddeliveryapi.store.domain.Store;

import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> findByNameAndCategory(String name, String category, String userAddress);
}
