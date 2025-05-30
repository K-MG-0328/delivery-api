package com.github.mingyu.fooddeliveryapi.domain.cart.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "아이템 옵션 VO")
public class ItemOption {
    private String itemId;
    private String optionName;
    private Integer price;
}
