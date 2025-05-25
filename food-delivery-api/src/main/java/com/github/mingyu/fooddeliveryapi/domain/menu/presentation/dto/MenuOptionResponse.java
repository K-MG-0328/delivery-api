package com.github.mingyu.fooddeliveryapi.domain.menu.presentation.dto;

import com.github.mingyu.fooddeliveryapi.domain.menu.domain.MenuOptionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "메뉴 옵션 응답 DTO")
public class MenuOptionResponse {

    @Schema(description = "메뉴 ID", example = "1")
    private Long menuId;

    @Schema(description = "옵션 ID", example = "1")
    private Long menuOptionId;

    @Schema(description = "메뉴 옵션 이름", example = "치즈 추가")
    private String option;

    @Schema(description = "추가 금액", example = "500")
    private int price;

    @Schema(description = "옵션 상태", example = "ACTIVE")
    private MenuOptionStatus status;
}
