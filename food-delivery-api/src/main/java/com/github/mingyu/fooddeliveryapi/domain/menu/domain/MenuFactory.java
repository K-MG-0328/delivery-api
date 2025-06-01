package com.github.mingyu.fooddeliveryapi.domain.menu.domain;

import com.github.mingyu.fooddeliveryapi.common.util.IdCreator;
import com.github.mingyu.fooddeliveryapi.domain.menu.application.dto.MenuOptionParam;
import com.github.mingyu.fooddeliveryapi.domain.menu.application.dto.MenuParam;

import java.util.ArrayList;
import java.util.List;

public class MenuFactory {

    private MenuFactory() {}

    public static Menu createMenu(MenuParam param) {
        String name = param.getName();
        String storeId = param.getStoreId();
        Integer price = param.getPrice();

        Menu menu;
        if(param.getOptions().isEmpty()) {
            menu = createMenu(storeId, name, price);
        }else{
            menu = createMenuWithOptions(storeId, name, price, param.getOptions());
        }
        return menu;
    }

    // 옵션이 없을 경우
    public static Menu createMenu(String storeId, String name, int price) {
        String menuId = IdCreator.randomUuid();
        MenuValidator.validateMenu(menuId, storeId, name, price, null);
        return new Menu(menuId, storeId, name, price, MenuStatus.ACTIVE, new ArrayList<>());
    }

    // 옵션 있을 경우
    public static Menu createMenuWithOptions(String storeId, String name, int price, List<MenuOptionParam> options) {
        String menuId = IdCreator.randomUuid();
        MenuValidator.validateMenu(menuId, storeId, name, price, options);
        Menu menu = new Menu(menuId, storeId, name, price, MenuStatus.ACTIVE, new ArrayList<>());
        createMenuOptionList(menu, options);
        return menu;
    }

    // 메뉴 옵션 목록 생성
    public static void createMenuOptionList(Menu menu, List<MenuOptionParam> options) {
        for (MenuOptionParam option : options) {
            String optionId = IdCreator.randomUuid();
            String optionName = option.getOptionName();
            int price = option.getPrice();
            MenuOption menuOption = new MenuOption(optionId, optionName, price, MenuOptionStatus.ACTIVE);
            menu.addMenuOption(menuOption);
        }
    }
}
