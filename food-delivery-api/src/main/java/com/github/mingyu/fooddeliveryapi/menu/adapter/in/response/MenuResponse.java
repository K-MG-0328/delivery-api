package com.github.mingyu.fooddeliveryapi.menu.adapter.in.response;

import com.github.mingyu.fooddeliveryapi.menu.domain.MenuStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "메뉴 응답 DTO")
@Getter
@AllArgsConstructor
public class MenuResponse {
    @Schema(description = "메뉴 ID", example = "1")
    private String menuId;

    @Schema(description = "가게 ID", example = "10")
    private String storeId;

    @Schema(description = "메뉴 이름", example = "불고기 덮밥")
    private String name;

    @Schema(description = "메뉴 가격", example = "8500")
    private int price;

    @Schema(description = "메뉴 상태", example = "ACTIVE")
    private MenuStatus status;

    @Schema(description = "메뉴 옵션 목록")
    private List<MenuOptionResponse> options;
}
