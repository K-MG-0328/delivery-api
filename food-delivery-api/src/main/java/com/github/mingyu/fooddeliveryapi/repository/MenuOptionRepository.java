package com.github.mingyu.fooddeliveryapi.repository;

import com.github.mingyu.fooddeliveryapi.entity.Menu;
import com.github.mingyu.fooddeliveryapi.entity.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {

    List<MenuOption> findByMenu(Menu menu);
}
