package com.github.mingyu.fooddeliveryapi.menu.adapter.in.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "메뉴 목록 응답 DTO")
@Getter
@AllArgsConstructor
public class MenuListResponse {
    @Schema(description = "메뉴 목록")
    private List<MenuResponse> menus  = new ArrayList<>();
}
