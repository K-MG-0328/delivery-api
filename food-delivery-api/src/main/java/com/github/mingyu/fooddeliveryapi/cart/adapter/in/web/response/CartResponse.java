package com.github.mingyu.fooddeliveryapi.cart.adapter.in.web.response;

import com.github.mingyu.fooddeliveryapi.cart.domain.vo.Item;
import com.github.mingyu.fooddeliveryapi.cart.domain.CartStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "장바구니 전체 응답 DTO")
public class CartResponse {

    @Schema(description = "장바구니 ID", example = "1")
    private String cartId;

    @Schema(description = "사용자 ID", example = "1")
    private String userId;

    @Schema(description = "가게 ID", example = "5")
    private String storeId;

    @Schema(description = "장바구니 상태", example = "ACTIVE")
    private CartStatus status;

    @Schema(description = "장바구니 항목 리스트")
    private List<Item> items;

    @Schema(description = "장바구니 총 금액", example = "23000")
    private Integer totalPrice;
}
