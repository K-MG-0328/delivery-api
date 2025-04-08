package com.github.mingyu.fooddeliveryapi.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 역할")
public enum UserRole {
    @Schema(description = "일반 사용자")
    USER,
    @Schema(description = "관리자")
    ADMIN
}
