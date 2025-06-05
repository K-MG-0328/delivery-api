package com.github.mingyu.fooddeliveryapi.menu.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "메뉴 옵션 상태")
public enum MenuOptionStatus {

    @Schema(description = "옵션 사용 가능")
    ACTIVE,

    @Schema(description = "옵션 비활성화됨")
    INACTIVE,
}
