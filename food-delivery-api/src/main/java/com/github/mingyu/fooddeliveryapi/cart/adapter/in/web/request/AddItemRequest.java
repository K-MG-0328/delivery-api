package com.github.mingyu.fooddeliveryapi.cart.adapter.in.web.request;

import com.github.mingyu.fooddeliveryapi.cart.domain.vo.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "장바구니 아이템 추가 요청")
public class AddItemRequest {

    @Schema(description = "사용자 ID", example = "1")
    private String userId;

    @Schema(description = "가게 ID", example = "2")
    private String storeId;

    @Schema(description = "아이템")
    private Item item;
}
