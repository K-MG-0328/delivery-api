package com.github.mingyu.fooddeliveryapi.domain.menu.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MenuRepository extends JpaRepository<Menu, String> {
    List<Menu> findByStore_StoreId(String storeId);

    List<Menu> findByMenuIdIn(Set<String> menuIds);
}
