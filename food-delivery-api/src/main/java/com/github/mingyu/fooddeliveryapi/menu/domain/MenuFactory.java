package com.github.mingyu.fooddeliveryapi.menu.domain;

import com.github.mingyu.fooddeliveryapi.common.util.IdCreator;
import com.github.mingyu.fooddeliveryapi.menu.application.port.in.command.MenuOptionCreateCommand;
import com.github.mingyu.fooddeliveryapi.menu.application.port.in.command.MenuCreateCommand;

import java.util.ArrayList;
import java.util.List;

public class MenuFactory {

    private MenuFactory() {}

    public static Menu createMenu(MenuCreateCommand command) {
        String name = command.getName();
        String storeId = command.getStoreId();
        Integer price = command.getPrice();

        Menu menu;
        if(command.getOptions().isEmpty()) {
            menu = createMenu(storeId, name, price);
        }else{
            menu = createMenuWithOptions(storeId, name, price, command.getOptions());
        }
        return menu;
    }

    // 옵션이 없을 경우
    public static Menu createMenu(String storeId, String name, int price) {
        String menuId = IdCreator.randomUuid();
        return new Menu(menuId, storeId, name, price, MenuStatus.ACTIVE, new ArrayList<>());
    }

    // 옵션 있을 경우
    public static Menu createMenuWithOptions(String storeId, String name, int price, List<MenuOptionCreateCommand> options) {
        String menuId = IdCreator.randomUuid();
        Menu menu = new Menu(menuId, storeId, name, price, MenuStatus.ACTIVE, new ArrayList<>());
        createMenuOptionList(menu, options);
        return menu;
    }

    // 메뉴 옵션 목록 생성
    public static void createMenuOptionList(Menu menu, List<MenuOptionCreateCommand> options) {
        for (MenuOptionCreateCommand option : options) {
            String optionId = IdCreator.randomUuid();
            String optionName = option.getOptionName();
            int price = option.getPrice();
            MenuOption menuOption = new MenuOption(optionId, optionName, price, MenuOptionStatus.ACTIVE);
            menu.addMenuOption(menuOption);
        }
    }
}
