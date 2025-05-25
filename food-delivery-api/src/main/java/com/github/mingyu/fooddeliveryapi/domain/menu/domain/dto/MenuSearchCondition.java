package com.github.mingyu.fooddeliveryapi.domain.menu.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "메뉴 검색 조건 DTO")
@Getter
@Setter
public class MenuSearchCondition {

    @Schema(description = "가게 아이디", example = "12")
    private Long storeId;
}
