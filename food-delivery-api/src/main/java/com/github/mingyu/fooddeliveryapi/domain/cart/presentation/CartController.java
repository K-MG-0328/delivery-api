package com.github.mingyu.fooddeliveryapi.domain.cart.presentation;

import com.github.mingyu.fooddeliveryapi.domain.cart.application.CartMapper;
import com.github.mingyu.fooddeliveryapi.domain.cart.application.CartService;
import com.github.mingyu.fooddeliveryapi.domain.cart.application.dto.CartParam;
import com.github.mingyu.fooddeliveryapi.domain.cart.application.dto.SingleCartParam;
import com.github.mingyu.fooddeliveryapi.domain.cart.domain.CartStatus;
import com.github.mingyu.fooddeliveryapi.domain.cart.presentation.dto.CartRequest;
import com.github.mingyu.fooddeliveryapi.domain.cart.presentation.dto.CartResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @Operation(summary = "장바구니 담기", description = "메뉴를 장바구니에 추가합니다.")
    @PostMapping("/cart")
    public void addToCartItem(@RequestBody CartRequest request){
        SingleCartParam cartParam = cartMapper.toCartParam(request);
        cartService.addToCartItem(cartParam);
    }

    @Operation(summary = "장바구니 조회", description = "현재 사용자의 장바구니 정보를 조회합니다.")
    @GetMapping("/cart/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long userId) {
        CartParam cartParam;
        try {
            cartParam = cartService.getCart(userId);
        }catch (IllegalStateException e){
            CartResponse response = new CartResponse();
            response.setStatus(CartStatus.EMPTY_CART);
            return ResponseEntity.ok(response);
        }
        CartResponse response = cartMapper.toCartResponse(cartParam);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "수량 수정", description = "장바구니에 담긴 특정 메뉴의 수량을 수정합니다.")
    @PatchMapping("/cart")
    public void updateItem(@RequestBody CartRequest request) {
        SingleCartParam cartParam = cartMapper.toCartParam(request);
        cartService.updateItem(cartParam);
    }

    @Operation(summary = "항목 삭제", description = "장바구니에서 특정 메뉴를 제거합니다.")
    @DeleteMapping("/cart")
    public void removeItem(@RequestBody CartRequest request) {
        SingleCartParam cartParam = cartMapper.toCartParam(request);
        cartService.removeItem(cartParam);
    }

    @Operation(summary = "장바구니 비우기", description = "장바구니에 담긴 모든 메뉴를 삭제합니다.")
    @DeleteMapping("/cart/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }
}
