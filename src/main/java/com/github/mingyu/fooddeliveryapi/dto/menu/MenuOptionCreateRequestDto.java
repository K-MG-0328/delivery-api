package com.github.mingyu.fooddeliveryapi.dto.menu;

import com.github.mingyu.fooddeliveryapi.enums.MenuOptionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "메뉴 옵션 생성 요청 DTO")
public class MenuOptionCreateRequestDto {

    @Schema(description = "메뉴 옵션 이름", example = "치즈 추가")
    private String option;

    @Schema(description = "추가 금액", example = "500")
    private int price;

    @Schema(description = "옵션 상태", example = "ACTIVE")
    private MenuOptionStatus status;
}
