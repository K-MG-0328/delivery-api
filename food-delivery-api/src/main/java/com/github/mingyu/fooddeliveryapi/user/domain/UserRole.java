package com.github.mingyu.fooddeliveryapi.user.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 역할")
public enum UserRole {
    @Schema(description = "일반 사용자")
    USER,
    @Schema(description = "라이더")
    RIDER,
    @Schema(description = "가게 사장")
    STORE_OWNER,
}
