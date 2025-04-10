package com.github.mingyu.fooddeliveryapi.controller;

import com.github.mingyu.fooddeliveryapi.dto.cart.CartResponseDto;
import com.github.mingyu.fooddeliveryapi.dto.cart.ItemAddRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.cart.CartUpdateDto;
import com.github.mingyu.fooddeliveryapi.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니 담기", description = "메뉴를 장바구니에 추가합니다.")
    @PostMapping("/cart")
    public ResponseEntity<Void> addToCartItem(@RequestBody ItemAddRequestDto request){
        cartService.addToCartItem(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "장바구니 조회", description = "현재 사용자의 장바구니 정보를 조회합니다.")
    @GetMapping("/cart/{userId}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable Long userId) {
        CartResponseDto cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "수량 수정", description = "장바구니에 담긴 특정 메뉴의 수량을 수정합니다.")
    @PatchMapping("/cart")
    public ResponseEntity<Void> updateQuantity(@RequestBody CartUpdateDto request) {
        cartService.updateQuantity(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "항목 삭제", description = "장바구니에서 특정 메뉴를 제거합니다.")
    @DeleteMapping("/cart")
    public ResponseEntity<Void> removeItem(@RequestBody CartUpdateDto request) {
        cartService.removeItem(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "장바구니 비우기", description = "장바구니에 담긴 모든 메뉴를 삭제합니다.")
    @DeleteMapping("/cart/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
