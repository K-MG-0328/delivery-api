package com.github.mingyu.fooddeliveryapi.menu.application.port.in;

import com.github.mingyu.fooddeliveryapi.menu.adapter.in.response.MenuListResponse;
import com.github.mingyu.fooddeliveryapi.menu.adapter.in.response.MenuResponse;
import com.github.mingyu.fooddeliveryapi.menu.application.port.in.command.MenuCreateCommand;
import com.github.mingyu.fooddeliveryapi.menu.application.port.in.command.MenuUpdateCommand;

public interface MenuCrudUseCase {
    void addMenu(MenuCreateCommand command);
    void deleteMenu(String menuId);
    void updateMenu(MenuUpdateCommand command);
    MenuResponse searchMenu(String menuId);
    MenuListResponse searchMenus(String storeId);
}
