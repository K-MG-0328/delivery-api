package com.github.mingyu.fooddeliveryapi.menu.application.port.in.command;

import com.github.mingyu.fooddeliveryapi.menu.domain.MenuOptionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuOptionUpdateCommand {
    private String menuOptionId;
    private String optionName;
    private int price;  // 옵션의 경우 값이 없어도 0 처리
    private MenuOptionStatus status;
}
