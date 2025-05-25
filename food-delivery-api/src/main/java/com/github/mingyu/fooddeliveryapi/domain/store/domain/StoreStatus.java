package com.github.mingyu.fooddeliveryapi.domain.store.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "가게 상태")
public enum StoreStatus {

    @Schema(description = "운영 중")
    ACTIVE,
    @Schema(description = "운영 일시 중지")
    INACTIVE,
    @Schema(description = "임시 중단")
    PAUSED,
    @Schema(description = "폐업 처리")
    CLOSED,
    @Schema(description = "삭제된 상태")
    DELETED
}
