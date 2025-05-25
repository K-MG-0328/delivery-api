package com.github.mingyu.fooddeliveryapi.domain.cart.domain.dto;

import com.github.mingyu.fooddeliveryapi.domain.cart.domain.CartStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "장바구니 전체 응답 DTO")
public class CartResponseDto {

    @Schema(description = "장바구니 ID", example = "1")
    private Long cartId;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "가게 ID", example = "5")
    private Long storeId;

    @Schema(description = "장바구니 상태", example = "ACTIVE")
    private CartStatus status;

    @Schema(description = "장바구니 항목 리스트")
    private List<CartItemResponseDto> items;

    @Schema(description = "장바구니 총 금액", example = "23000")
    private int totalPrice;

    public static CartResponseDto empty(Long userId) {
        CartResponseDto dto = new CartResponseDto();
        dto.setUserId(userId);
        dto.setStoreId(null);
        dto.setStatus(CartStatus.EMPTY_CART);
        dto.setItems(null);
        dto.setTotalPrice(0);
        return dto;
    }
}
