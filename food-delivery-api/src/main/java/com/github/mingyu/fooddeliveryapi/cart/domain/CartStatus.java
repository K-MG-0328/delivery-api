package com.github.mingyu.fooddeliveryapi.cart.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "장바구니 상태")
public enum CartStatus {

    @Schema(description = "상품이 담겨서 활성화된 상태")
    ACTIVE,

    @Schema(description = "장바구니가 비워져 있는 초기 상태")
    EMPTY_CART,

    @Schema(description = "결제가 완료되어 주문 생성까지 진행된 상태")
    PAID
}
