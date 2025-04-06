package com.github.mingyu.fooddeliveryapi.repository;

import com.github.mingyu.fooddeliveryapi.entity.Store;

import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> findByNameAndCategory(String name, String category, String userAddress);
}
