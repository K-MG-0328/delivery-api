package com.github.mingyu.fooddeliveryapi.user.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 상태")
public enum UserStatus {
    @Schema(description = "활성 상태")
    ACTIVE,
    @Schema(description = "탈퇴 상태")
    DELETED
}
