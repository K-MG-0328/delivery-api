package com.github.mingyu.fooddeliveryapi.menu.adapter.in.web.request;

import com.github.mingyu.fooddeliveryapi.menu.domain.MenuOptionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "메뉴 옵션 생성 요청 DTO")
@Getter
@AllArgsConstructor
public class MenuOptionUpdateRequest {

    @Schema(description = "메뉴 옵션 이름", example = "치즈 추가")
    private final String menuOptionId;

    @Schema(description = "메뉴 옵션 이름", example = "치즈 추가")
    private String optionName;

    @Schema(description = "추가 금액", example = "500")
    private int price;

    @Schema(description = "메뉴 옵션 상태", example = "ACTIVE")
    private MenuOptionStatus status;
}
