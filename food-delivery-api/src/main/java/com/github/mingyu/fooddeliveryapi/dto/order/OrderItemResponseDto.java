package com.github.mingyu.fooddeliveryapi.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "주문 항목 응답 DTO")
public class OrderItemResponseDto {

    @Schema(description = "메뉴 ID", example = "101")
    private Long menuId;

    @Schema(description = "메뉴 이름", example = "불고기 덮밥")
    private String menuName;

    @Schema(description = "주문 수량", example = "2")
    private int quantity;

    @Schema(description = "메뉴 가격", example = "12000")
    private int menuPrice;

    @Schema(description = "선택한 옵션 목록")
    private List<OrderItemOptionResponseDto> options;
}
