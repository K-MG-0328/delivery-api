package com.github.mingyu.fooddeliveryapi.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 상태")
public enum UserStatus {
    @Schema(description = "활성 상태")
    ACTIVE,
    @Schema(description = "비활성 상태")
    INACTIVE,
    @Schema(description = "탈퇴 상태")
    DELETED,
    @Schema(description = "차단 상태")
    BANNED
}
