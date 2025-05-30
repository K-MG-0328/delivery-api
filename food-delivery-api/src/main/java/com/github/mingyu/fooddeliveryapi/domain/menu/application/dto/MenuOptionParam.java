package com.github.mingyu.fooddeliveryapi.domain.menu.application.dto;

import com.github.mingyu.fooddeliveryapi.domain.menu.domain.MenuOptionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuOptionParam {
    private String menuOptionId;
    private String optionName;
    private int price;  // 옵션의 경우 값이 없어도 0 처리
    private MenuOptionStatus status;
}
