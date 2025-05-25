package com.github.mingyu.fooddeliveryapi.domain.menu.application;

import com.github.mingyu.fooddeliveryapi.domain.menu.domain.Menu;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.dto.MenuCreateRequestDto;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.dto.MenuResponseDto;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.dto.MenuUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MenuOptionMapper.class})
public interface MenuMapper {

    Menu toEntity(MenuCreateRequestDto dto);
    MenuResponseDto toDto(Menu menu);

    void updateFromDto(MenuUpdateRequestDto dto, @MappingTarget Menu menu);
    List<MenuResponseDto> toDtoList(List<Menu> menus);
}
