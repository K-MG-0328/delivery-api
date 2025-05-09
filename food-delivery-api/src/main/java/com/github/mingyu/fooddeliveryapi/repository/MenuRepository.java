package com.github.mingyu.fooddeliveryapi.repository;

import com.github.mingyu.fooddeliveryapi.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByStore_StoreId(Long storeStoreId);

    List<Menu> findByMenuIdIn(Set<Long> menuIds);
}
