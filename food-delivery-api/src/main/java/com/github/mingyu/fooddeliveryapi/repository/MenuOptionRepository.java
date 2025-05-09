package com.github.mingyu.fooddeliveryapi.repository;

import com.github.mingyu.fooddeliveryapi.entity.Menu;
import com.github.mingyu.fooddeliveryapi.entity.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {

    List<MenuOption> findByMenu(Menu menu);


    List<MenuOption> findByMenu_MenuId(Long menuId);

    //menus 리스트에 포함된 값들과 일치하는 모든 MenuOption을 조회
    List<MenuOption> findByMenuIn(List<Menu> menus);

    List<MenuOption> findByMenuOptionIdIn(Set<Long> optionIds);
}
