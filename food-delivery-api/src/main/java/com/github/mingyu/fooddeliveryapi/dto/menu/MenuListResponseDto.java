package com.github.mingyu.fooddeliveryapi.dto.menu;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "메뉴 목록 응답 DTO")
public class MenuListResponseDto {
    @Schema(description = "메뉴 목록")
    private List<MenuResponseDto> menus  = new ArrayList<>();
}
