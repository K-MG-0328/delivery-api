package com.github.mingyu.fooddeliveryapi.menu.application.port.out;

import com.github.mingyu.fooddeliveryapi.menu.domain.Menu;

import java.util.List;

public interface MenuRepositoryPort {
    void save(Menu menu);

    void delete(Menu menu);

    List<Menu> findByStore_StoreId(String storeId);

    Menu findById(String menuId);
}
