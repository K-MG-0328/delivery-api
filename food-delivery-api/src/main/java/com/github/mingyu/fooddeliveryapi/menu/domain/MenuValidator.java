package com.github.mingyu.fooddeliveryapi.menu.domain;

import com.github.mingyu.fooddeliveryapi.menu.domain.exception.InvalidMenuException;

import java.util.ArrayList;
import java.util.List;

public class MenuValidator {


    public static void validateMenu(Menu menu) {
        validateMenu(menu.getMenuId(), menu.getStoreId(), menu.getName(), menu.getPrice(), menu.getStatus(), menu.getOptions());
    }

    public static void validateMenu(String menuId, String storeId, String name, Integer price, MenuStatus status, List<MenuOption> options) {
        List<String> errors = new ArrayList<>();

        // 메뉴 id 검증
        if (menuId == null || menuId.trim().isEmpty()) {
            errors.add("메뉴 ID는 필수입니다.");
        }

        // 가게 id 검증
        if (storeId == null || storeId.trim().isEmpty()) {
            errors.add("가게 ID는 필수입니다.");
        }

        // 이름 검증
        if (name == null || name.trim().isEmpty()) {
            errors.add("메뉴 이름은 비어 있을 수 없습니다.");
        }

        // 가격 검증
        if (price == null || price < 0) {
            errors.add("메뉴 가격은 0 이상이어야 합니다.");
        }

        // 상태 검증
        if (status == null) {
            errors.add("메뉴 상태는 필수입니다.");
        }

        // 메뉴 옵션 검증
        if (options != null) {
            for (MenuOption option : options) {
                validateMenuOption(option, errors);
            }
        }

        if (!errors.isEmpty()) {
            throw new InvalidMenuException(errors);
        }
    }

    private static void validateMenuOption(MenuOption option, List<String> errors) {
        if (option.getMenuOptionId() == null || option.getMenuOptionId().trim().isEmpty()) {
            errors.add("메뉴 옵션 ID는 필수입니다.");
        }

        if (option.getOptionName() == null || option.getOptionName().trim().isEmpty()) {
            errors.add("옵션 이름은 비어 있을 수 없습니다.");
        }

        if (option.getPrice() == null || option.getPrice() < 0) {
            errors.add("옵션 가격은 0 이상이어야 합니다.");
        }

        if (option.getStatus() == null) {
            errors.add("옵션 상태는 필수입니다.");
        }
    }
}
