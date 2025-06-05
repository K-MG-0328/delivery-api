package com.github.mingyu.fooddeliveryapi.domain.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주문 상태")
public enum OrderStatus {

    @Schema(description = "주문 생성")
    CREATED,
    @Schema(description = "주문 실패")
    FAILED,
    @Schema(description = "주문 결제")
    PAID,
    @Schema(description = "가게가 주문을 접수한 상태")
    CONFIRMED,
    @Schema(description = "음식 조리 중")
    COOKING,
    @Schema(description = "배달 중")
    DELIVERING,
    @Schema(description = "배달 완료됨")
    DELIVERED,
    @Schema(description = "주문이 취소됨")
    CANCELED
}
