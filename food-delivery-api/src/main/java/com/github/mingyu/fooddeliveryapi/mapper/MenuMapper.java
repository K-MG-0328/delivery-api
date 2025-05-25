package com.github.mingyu.fooddeliveryapi.mapper;

import com.github.mingyu.fooddeliveryapi.dto.menu.MenuCreateRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.menu.MenuResponseDto;
import com.github.mingyu.fooddeliveryapi.dto.menu.MenuUpdateRequestDto;
import com.github.mingyu.fooddeliveryapi.entity.Menu;
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
