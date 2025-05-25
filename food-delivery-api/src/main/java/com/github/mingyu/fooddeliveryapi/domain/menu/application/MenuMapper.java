package com.github.mingyu.fooddeliveryapi.domain.menu.application;

import com.github.mingyu.fooddeliveryapi.domain.menu.domain.Menu;
import com.github.mingyu.fooddeliveryapi.domain.menu.presentation.dto.MenuCreateRequest;
import com.github.mingyu.fooddeliveryapi.domain.menu.presentation.dto.MenuResponse;
import com.github.mingyu.fooddeliveryapi.domain.menu.presentation.dto.MenuUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MenuOptionMapper.class})
public interface MenuMapper {

    Menu toEntity(MenuCreateRequest dto);
    MenuResponse toDto(Menu menu);

    void updateFromDto(MenuUpdateRequest dto, @MappingTarget Menu menu);
    List<MenuResponse> toDtoList(List<Menu> menus);
}
