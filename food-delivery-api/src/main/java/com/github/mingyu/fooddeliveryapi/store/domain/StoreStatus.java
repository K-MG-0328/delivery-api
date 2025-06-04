package com.github.mingyu.fooddeliveryapi.store.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "가게 상태")
public enum StoreStatus {

    @Schema(description = "운영 중")
    OPEN,
    @Schema(description = "휴점")
    TEMPORARILY_CLOSED,
    @Schema(description = "폐업")
    CLOSED
}
