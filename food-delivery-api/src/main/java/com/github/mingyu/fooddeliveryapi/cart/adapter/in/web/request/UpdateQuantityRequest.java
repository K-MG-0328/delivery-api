package com.github.mingyu.fooddeliveryapi.cart.adapter.in.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "장바구니 아이템 수량 수정 요청")
public class UpdateQuantityRequest {

    @Schema(description = "사용자 ID", example = "1")
    private final String userId;
    @Schema(description = "아이템 ID", example = "1")
    private final String itemId;
    @Schema(description = "수량", example = "1")
    private final Integer quantity;
}
