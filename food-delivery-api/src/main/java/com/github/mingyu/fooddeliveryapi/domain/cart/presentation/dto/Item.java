package com.github.mingyu.fooddeliveryapi.domain.cart.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "아이템 VO")
public class Item {

    @Schema(description = "아이템 ID", example = "101")
    private String itemId;

    @Schema(description = "메뉴 ID", example = "101")
    private String menuId;

    @Schema(description = "메뉴 이름", example = "커피")
    private String name;

    @Schema(description = "가격", example = "5000")
    private Integer price;

    @Schema(description = "선택한 메뉴 옵션 목록", example = "[1, 2]")
    private List<ItemOption> options;

    @Schema(description = "수량", example = "2")
    private Integer quantity;
}
