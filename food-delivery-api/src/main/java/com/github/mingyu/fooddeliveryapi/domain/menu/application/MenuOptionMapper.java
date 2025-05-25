package com.github.mingyu.fooddeliveryapi.domain.menu.application;

import com.github.mingyu.fooddeliveryapi.domain.menu.domain.MenuOption;
import com.github.mingyu.fooddeliveryapi.domain.menu.presentation.dto.MenuOptionCreateRequest;
import com.github.mingyu.fooddeliveryapi.domain.menu.presentation.dto.MenuOptionResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuOptionMapper {

    MenuOption toEntity(MenuOptionCreateRequest dto);

    MenuOptionResponse toDto(MenuOption option);

    List<MenuOptionResponse> toDtoList(List<MenuOption> entities);
}
