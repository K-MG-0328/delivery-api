package com.github.mingyu.fooddeliveryapi.domain.delivery.domain;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "배송 상태 Enum")
public enum DeliveryState {

    @Schema(description = "배송 준비 중")
    PREPARING,

    @Schema(description = "배송 시작")
    STARTED,

    @Schema(description = "배송 중")
    IN_DELIVERY,

    @Schema(description = "배송 완료")
    DELIVERED,

    @Schema(description = "배송 취소됨")
    CANCELED
}
