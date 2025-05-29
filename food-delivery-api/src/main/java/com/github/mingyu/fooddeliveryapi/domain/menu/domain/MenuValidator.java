package com.github.mingyu.fooddeliveryapi.domain.menu.domain;

import com.github.mingyu.fooddeliveryapi.domain.menu.application.dto.MenuOptionParam;
import com.github.mingyu.fooddeliveryapi.domain.menu.application.dto.MenuParam;

import java.util.List;

public class MenuValidator {

    public static void validateMenuParam(MenuParam param) {
        validateMenu(param.getMenuId(), param.getStoreId(), param.getName(), param.getPrice(), param.getOptions());
    }

    //메뉴 검증
    public static void validateMenu(String menuId, String storeId, String name, Integer price, List<MenuOptionParam> options){
        // 메뉴 id 검증
        if (menuId == null) {
            throw new IllegalArgumentException("메뉴 id가 존재하지않습니다.");
        }
        // 가게 id 검증
        if (storeId == null) {
            throw new IllegalArgumentException("가게 id가 존재하지않습니다.");
        }
        // 이름 검증
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("메뉴 이름은 비어 있을 수 없습니다.");
        }
        // 가격 검증
        if (price < 0) {
            throw new IllegalArgumentException("메뉴 가격은 0 이상이어야 합니다.");
        }

        validateMenuOption(options);
    }

    //메뉴 옵션 검증
    public static void validateMenuOption(List<MenuOptionParam> options){

        // 옵션 리스트 null
        if (options == null) {
            return;
        }

        // 옵션 이름 중복 금지
        long uniqueOptionNameCount = options.stream()
                .map(MenuOptionParam::getOptionName)
                .distinct()
                .count();

        if (uniqueOptionNameCount != options.size()) {
            throw new IllegalArgumentException("메뉴 옵션 이름은 중복될 수 없습니다.");
        }
    }
}
