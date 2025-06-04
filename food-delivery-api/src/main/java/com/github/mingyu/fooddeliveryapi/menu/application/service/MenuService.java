package com.github.mingyu.fooddeliveryapi.menu.application.service;

import com.github.mingyu.fooddeliveryapi.menu.adapter.in.response.MenuListResponse;
import com.github.mingyu.fooddeliveryapi.menu.adapter.in.response.MenuOptionResponse;
import com.github.mingyu.fooddeliveryapi.menu.adapter.in.response.MenuResponse;
import com.github.mingyu.fooddeliveryapi.menu.application.port.in.MenuCrudUseCase;
import com.github.mingyu.fooddeliveryapi.menu.application.port.in.command.MenuCreateCommand;
import com.github.mingyu.fooddeliveryapi.menu.application.port.in.command.MenuUpdateCommand;
import com.github.mingyu.fooddeliveryapi.menu.application.port.out.MenuRepositoryPort;
import com.github.mingyu.fooddeliveryapi.menu.domain.Menu;
import com.github.mingyu.fooddeliveryapi.menu.domain.MenuFactory;
import com.github.mingyu.fooddeliveryapi.menu.domain.MenuOption;
import com.github.mingyu.fooddeliveryapi.menu.domain.MenuValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService implements MenuCrudUseCase {

    private final MenuRepositoryPort menuRepositoryPort;

    /*메뉴 추가*/
    @Override
    @Transactional
    public void addMenu(MenuCreateCommand command) {
        Menu menu = MenuFactory.createMenu(command);
        MenuValidator.validateMenu(menu);
        menuRepositoryPort.save(menu);
    }

    /*메뉴 삭제*/
    @Override
    @Transactional
    public void deleteMenu(String menuId) {
        Menu menu = getMenu(menuId);

        /*QueryDSL로 한번의 쿼리로 삭제되도록 구현 N+1 문제 방지*/
        menuRepositoryPort.delete(menu);
    }

    /*메뉴 업데이트*/
    @Override
    @Transactional
    public void updateMenu(MenuUpdateCommand command) {
        Menu menu = getMenu(command.getMenuId());

        List<MenuOption> options = command.getOptions().stream().map(option ->
            new MenuOption(option.getMenuOptionId(), option.getOptionName(), option.getPrice(), option.getStatus())
        ).toList();

        menu.update(command.getName(), command.getPrice(), command.getStatus(), options);
        menuRepositoryPort.save(menu);
    }

    /*단일 메뉴 목록 가져오기*/
    @Override
    @Transactional(readOnly = true)
    public MenuResponse searchMenu(String menuId) {
        Menu menu = getMenu(menuId);
        List<MenuOptionResponse> options = menu.getOptions().stream().map(option -> new MenuOptionResponse(option.getMenu().getMenuId(),
                option.getMenuOptionId(),
                option.getOptionName(),
                option.getPrice(),
                option.getStatus())
        ).toList();
        return new MenuResponse(menu.getMenuId(), menu.getStoreId(), menu.getName(), menu.getPrice(), menu.getStatus(), options);
    }

    /*가게 메뉴 목록 가져오기*/
    @Override
    @Transactional(readOnly = true)
    public MenuListResponse searchMenus(String storeId) {
        List<Menu> menus = menuRepositoryPort.findByStore_StoreId(storeId);

        /*@EntityGraph(attributePaths = "options")로 N+1 문제 해결*/
        List<MenuResponse> menuList = menus.stream().map(menu -> {
            List<MenuOptionResponse> list = menu.getOptions().stream().map(option -> new MenuOptionResponse(option.getMenu().getMenuId(),
                    option.getMenuOptionId(),
                    option.getOptionName(),
                    option.getPrice(),
                    option.getStatus())).toList();
            return new MenuResponse(menu.getMenuId(), menu.getStoreId(), menu.getName(), menu.getPrice(), menu.getStatus(), list);
        }).toList();

        return new MenuListResponse(menuList);
    }

    @Transactional(readOnly = true)
    public Menu getMenu(String menuId) {
        Menu menu= menuRepositoryPort.findById(menuId);
        return menu;
    }
}
