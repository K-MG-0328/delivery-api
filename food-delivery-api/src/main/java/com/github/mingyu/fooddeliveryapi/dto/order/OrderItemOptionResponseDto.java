package com.github.mingyu.fooddeliveryapi.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "주문 항목 옵션 응답 DTO")
public class OrderItemOptionResponseDto {

    @Schema(description = "옵션 이름", example = "치즈 추가")
    private String optionName;

    @Schema(description = "옵션 가격", example = "1000")
    private int optionPrice;
}
