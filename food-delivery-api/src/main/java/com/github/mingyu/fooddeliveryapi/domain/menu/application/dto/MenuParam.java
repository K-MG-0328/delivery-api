package com.github.mingyu.fooddeliveryapi.domain.menu.application.dto;

import com.github.mingyu.fooddeliveryapi.domain.menu.domain.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuParam {
    private String menuId;
    private String storeId;
    private String name;
    private Integer price;
    private MenuStatus status;
    private List<MenuOptionParam> options;
}
