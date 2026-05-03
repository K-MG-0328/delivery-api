package com.github.mingyu.fooddeliveryapi.store.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreSearchCommand {
    private String name;
    private String category;
    private String deliveryAreas;
}
