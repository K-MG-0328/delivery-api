package com.github.mingyu.fooddeliveryapi.cart.adapter.in.web.request;

import com.github.mingyu.fooddeliveryapi.cart.domain.vo.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "장바구니 아이템 옵션 수정 요청")
public class UpdateItemOptionRequest {
    @Schema(description = "사용자 ID", example = "1")
    private final String userId;
    @Schema(description = "아이템")
    private final Item item;
}
