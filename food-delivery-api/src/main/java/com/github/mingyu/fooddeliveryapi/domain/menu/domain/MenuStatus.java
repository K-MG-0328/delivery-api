package com.github.mingyu.fooddeliveryapi.domain.menu.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "메뉴 상태")
public enum MenuStatus {
    @Schema(description = "메뉴가 정상적으로 등록되어 주문이 가능한 상태")
    ACTIVE,
    @Schema(description = "메뉴가 숨겨져 있으며 사용자에게 노출되지 않는 상태")
    INACTIVE,
    @Schema(description = "메뉴가 품절된 상태")
    SOLD_OUT
}