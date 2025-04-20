package com.github.mingyu.fooddeliveryapi.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "장바구니 항목 응답 DTO")
public class CartItemResponseDto {

    @Schema(description = "메뉴 ID", example = "101")
    private Long menuId;

    @Schema(description = "메뉴 이름", example = "불고기 덮밥")
    private String menuName;

    @Schema(description = "옵션 이름 목록", example = "[\"치즈 추가\", \"곱빼기\"]")
    private List<String> optionNames;

    @Schema(description = "수량", example = "2")
    private int quantity;

    @Schema(description = "해당 항목 총 금액", example = "17000")
    private int itemTotalPrice;
}
