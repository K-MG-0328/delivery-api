package com.github.mingyu.fooddeliveryapi.domain.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "결제 방식")
public enum PaymentMethod {
    @Schema(description = "카드")
    CARD,
    @Schema(description = "네이버 페이")
    NAVER_PAY
}
