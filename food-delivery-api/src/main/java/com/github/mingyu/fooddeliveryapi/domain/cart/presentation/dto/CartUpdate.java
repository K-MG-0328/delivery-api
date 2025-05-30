package com.github.mingyu.fooddeliveryapi.domain.cart.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "장바구니 변경 요청 DTO")
public class CartUpdate {

    @Schema(description = "장바구니 ID", example = "1")
    private Long cartId;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "장바구니 메뉴 ID", example = "101")
    private Long cartItemId;

    @Schema(description = "장바구니 옵션", example = "json")
    private String options;

    @Schema(description = "변경할 수량", example = "3")
    private int quantity;

}
