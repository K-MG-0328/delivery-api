package com.github.mingyu.fooddeliveryapi.menu.application.port.in.command;

import com.github.mingyu.fooddeliveryapi.menu.domain.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuUpdateCommand {
    private String menuId;
    private String storeId;
    private String name;
    private Integer price;
    private MenuStatus status;
    private List<MenuOptionUpdateCommand> options;
}
