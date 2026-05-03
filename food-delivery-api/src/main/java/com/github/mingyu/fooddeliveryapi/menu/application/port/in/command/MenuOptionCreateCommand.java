package com.github.mingyu.fooddeliveryapi.menu.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuOptionCreateCommand {
    private String optionName;
    private int price;  // 옵션의 경우 값이 없어도 0 처리
}
