package com.github.mingyu.fooddeliveryapi.domain.cart.presentation.dto;

import com.github.mingyu.fooddeliveryapi.domain.cart.domain.CartItemOption;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "장바구니에 메뉴 추가 요청 DTO")
public class CartItemAddRequest {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "가게 ID", example = "2")
    private Long storeId;

    @Schema(description = "메뉴 ID", example = "101")
    private Long menuId;

    @Schema(description = "메뉴 이름", example = "커피")
    private Long menuName;

    @Schema(description = "가격", example = "5000")
    private int price;

    @Schema(description = "선택한 메뉴 옵션 목록", example = "[1, 2]")
    private List<CartItemOption> options;

    @Schema(description = "수량", example = "2")
    private int quantity;


}
