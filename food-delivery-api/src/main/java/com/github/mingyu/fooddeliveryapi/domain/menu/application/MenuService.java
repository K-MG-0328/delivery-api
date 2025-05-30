package com.github.mingyu.fooddeliveryapi.domain.menu.application;

import com.github.mingyu.fooddeliveryapi.domain.menu.application.dto.MenuParam;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    /*메뉴 추가*/
    @Transactional
    public void addMenu(MenuParam param) {
        Menu menu = MenuFactory.createMenu(param);
        menuRepository.save(menu);
    }

    /*메뉴 삭제*/
    @Transactional
    public void deleteMenu(String menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("메뉴를 찾을 수 없습니다."));
        menuRepository.delete(menu);
    }

    /*메뉴 업데이트*/
    @Transactional
    public void updateMenu(String menuId, MenuParam param) {

        //메뉴 파라미터 검증
        MenuValidator.validateMenuParam(param);

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        //메뉴 수정
        menu.update(param);
        menuRepository.save(menu);
    }

    /*단일 메뉴 목록 가져오기*/
    @Transactional(readOnly = true)
    public MenuParam getMenu(String menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        MenuParam menuParam = menuMapper.toMenuParam(menu);
        return menuParam;
    }

    /*가게 메뉴 목록 가져오기*/
    @Transactional(readOnly = true)
    public List<MenuParam> searchMenus(String storeId) {
        List<Menu> menus = menuRepository.findByStore_StoreId(storeId);
        List<MenuParam> menuParams = menuMapper.toMenuParams(menus);
        return menuParams;
    }
}
