package com.github.mingyu.fooddeliveryapi.domain.menu.application;

import com.github.mingyu.fooddeliveryapi.domain.menu.application.dto.MenuParam;
import com.github.mingyu.fooddeliveryapi.domain.menu.application.dto.MenuOptionParam;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.Menu;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.MenuOption;
import com.github.mingyu.fooddeliveryapi.domain.menu.presentation.dto.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuParam toMenuParam(MenuCreateRequest request);
    MenuOptionParam toMenuOptionParam(MenuOptionCreateRequest request);

    MenuParam toMenuParam(MenuUpdateRequest request);
    MenuOptionParam toMenuOptionParam(MenuOptionUpdateRequest request);


    MenuResponse toDto(Menu menu);
    List<MenuOptionResponse> toDtoList(List<MenuOption> entities);
}
