package com.github.mingyu.fooddeliveryapi.domain.menu.application;

import com.github.mingyu.fooddeliveryapi.domain.menu.domain.MenuOption;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.dto.MenuOptionCreateRequestDto;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.dto.MenuOptionResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuOptionMapper {

    MenuOption toEntity(MenuOptionCreateRequestDto dto);

    MenuOptionResponseDto toDto(MenuOption option);

    List<MenuOptionResponseDto> toDtoList(List<MenuOption> entities);
}
