package com.github.mingyu.fooddeliveryapi.menu.adapter.out.persistence;

import com.github.mingyu.fooddeliveryapi.menu.domain.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, String>, MenuRepositoryCustom {

    @EntityGraph(attributePaths = "options")
    List<Menu> findByStore_StoreId(String storeId);
}
