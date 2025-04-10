package com.github.mingyu.fooddeliveryapi.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주문 상태")
public enum OrderStatus {

    @Schema(description = "주문 생성됨 (결제 직후 상태)")
    CREATED,
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
