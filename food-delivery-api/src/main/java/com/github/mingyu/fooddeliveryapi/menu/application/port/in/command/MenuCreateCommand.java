package com.github.mingyu.fooddeliveryapi.menu.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuCreateCommand {
    private String storeId;
    private String name;
    private Integer price;
    private List<MenuOptionCreateCommand> options;
}
