package com.github.mingyu.fooddeliveryapi.domain.menu.presentation.dto;
import io.swagger.v3.oas.annotations.media.Schema;

import com.github.mingyu.fooddeliveryapi.domain.menu.domain.MenuStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "메뉴 수정 요청 DTO")
public class MenuUpdateRequest {

    @Schema(description = "메뉴 ID", example = "1")
    private Long menuId;
    @Schema(description = "가게 ID", example = "10")
    private Long storeId;
    @Schema(description = "메뉴 이름", example = "불고기 덮밥")
    private String name;
    @Schema(description = "메뉴 가격", example = "8500")
    private int price;
    @Schema(description = "메뉴 상태", example = "ACTIVE")
    private MenuStatus status;
    @Schema(description = "메뉴 옵션 목록")
    private List<MenuOptionUpdateRequest> options;
}
