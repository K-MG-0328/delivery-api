package com.github.mingyu.fooddeliveryapi.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "장바구니 전체 응답 DTO")
public class CartResponseDto {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "가게 ID", example = "5")
    private Long storeId;

    @Schema(description = "장바구니 항목 리스트")
    private List<CartItemResponseDto> items;

    @Schema(description = "장바구니 총 금액", example = "23000")
    private int totalPrice;
}
