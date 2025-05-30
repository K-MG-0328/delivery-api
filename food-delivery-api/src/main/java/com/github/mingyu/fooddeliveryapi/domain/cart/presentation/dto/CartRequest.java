package com.github.mingyu.fooddeliveryapi.domain.cart.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "장바구니 CRUD 요청 DTO")
public class CartRequest {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "가게 ID", example = "2")
    private String storeId;

    @Schema(description = "아이템")
    private Item item;
}
