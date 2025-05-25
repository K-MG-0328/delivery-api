package com.github.mingyu.fooddeliveryapi.domain.cart.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "장바구니 상태")
public enum CartStatus {

    @Schema(description = "상품이 담겨서 활성화된 상태")
    ACTIVE,

    @Schema(description = "배송 완료 상태")
    DELIVERY_COMPLETE,

    @Schema(description = "결제가 완료되어 주문 생성까지 진행된 상태")
    PAID,

    @Schema(description = "주문이 취소되어 장바구니가 초기화된 상태")
    CANCELLED,

    @Schema(description = "장바구니가 비워져 있는 초기 상태")
    EMPTY_CART
}
