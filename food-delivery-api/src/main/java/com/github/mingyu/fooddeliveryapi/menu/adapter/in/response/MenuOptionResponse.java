package com.github.mingyu.fooddeliveryapi.menu.adapter.in.response;

import com.github.mingyu.fooddeliveryapi.menu.domain.MenuOptionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "메뉴 옵션 응답 DTO")
@Getter
@AllArgsConstructor
public class MenuOptionResponse {

    @Schema(description = "메뉴 ID", example = "1")
    private String menuId;

    @Schema(description = "옵션 ID", example = "1")
    private String menuOptionId;

    @Schema(description = "메뉴 옵션 이름", example = "치즈 추가")
    private String optionName;

    @Schema(description = "추가 금액", example = "500")
    private int price;

    @Schema(description = "옵션 상태", example = "ACTIVE")
    private MenuOptionStatus status;
}
