package com.github.mingyu.fooddeliveryapi.domain.store.domain;

import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> findByNameAndCategory(String name, String category, String userAddress);
}
