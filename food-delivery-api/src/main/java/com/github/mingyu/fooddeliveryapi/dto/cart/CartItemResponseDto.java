package com.github.mingyu.fooddeliveryapi.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "장바구니 항목 응답 DTO")
public class CartItemResponseDto {

    @Schema(description = "메뉴 ID", example = "101")
    private Long menuId;

    @Schema(description = "메뉴 이름", example = "불고기 덮밥")
    private String name;

    @Schema(description = "옵션 이름 목록 Json", example = "[\"치즈 추가\", \"곱빼기\"]")
    private String options;

    @Schema(description = "수량", example = "2")
    private int quantity;

    @Schema(description = "메뉴 금액", example = "17000")
    private int price;
}
