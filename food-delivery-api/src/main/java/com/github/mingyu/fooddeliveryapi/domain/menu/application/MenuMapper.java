package com.github.mingyu.fooddeliveryapi.domain.menu.application;

import com.github.mingyu.fooddeliveryapi.domain.menu.application.dto.MenuParam;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.Menu;
import com.github.mingyu.fooddeliveryapi.domain.menu.presentation.dto.MenuCreateRequest;
import com.github.mingyu.fooddeliveryapi.domain.menu.presentation.dto.MenuResponse;
import com.github.mingyu.fooddeliveryapi.domain.menu.presentation.dto.MenuUpdateRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuParam toMenuParam(MenuCreateRequest request);
    MenuParam toMenuParam(MenuUpdateRequest request);
    MenuParam toMenuParam(Menu menu);

    List<MenuParam> toMenuParams(List<Menu> menu);
    List<MenuResponse> toMenuResponses(List<MenuParam> menu);

    MenuResponse toMenuResponse(MenuParam param);

}
