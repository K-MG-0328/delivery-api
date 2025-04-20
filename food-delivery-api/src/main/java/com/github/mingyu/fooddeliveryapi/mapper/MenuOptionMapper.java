package com.github.mingyu.fooddeliveryapi.mapper;

import com.github.mingyu.fooddeliveryapi.dto.menu.MenuOptionCreateRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.menu.MenuOptionResponseDto;
import com.github.mingyu.fooddeliveryapi.entity.MenuOption;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuOptionMapper {

    MenuOption toEntity(MenuOptionCreateRequestDto dto);

    MenuOptionResponseDto toDto(MenuOption option);

    List<MenuOptionResponseDto> toDtoList(List<MenuOption> entities);
}
