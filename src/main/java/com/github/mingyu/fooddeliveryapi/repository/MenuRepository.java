package com.github.mingyu.fooddeliveryapi.repository;

import com.github.mingyu.fooddeliveryapi.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
