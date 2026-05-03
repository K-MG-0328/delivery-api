package com.github.mingyu.fooddeliveryapi.cart.adapter.in.web;

import com.github.mingyu.fooddeliveryapi.cart.adapter.in.web.request.AddItemRequest;
import com.github.mingyu.fooddeliveryapi.cart.adapter.in.web.request.UpdateItemOptionRequest;
import com.github.mingyu.fooddeliveryapi.cart.adapter.in.web.request.UpdateQuantityRequest;
import com.github.mingyu.fooddeliveryapi.cart.adapter.in.web.response.CartResponse;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.AddItemCommand;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.RemoveItemCommand;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.UpdateItemOptionCommand;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.UpdateQuantityCommand;
import com.github.mingyu.fooddeliveryapi.cart.application.service.CartService;
import com.github.mingyu.fooddeliveryapi.cart.domain.exception.InvalidCartException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니 담기", description = "메뉴를 장바구니에 추가합니다.")
    @PostMapping("/cart")
    public void addToCartItem(@RequestBody AddItemRequest request){
        cartService.addToCartItem(new AddItemCommand(request.getUserId(),
                request.getStoreId(),
                request.getItem()
        ));
    }

    @Operation(summary = "장바구니 조회", description = "현재 사용자의 장바구니 정보를 조회합니다.")
    @GetMapping("/cart/{userId}")
    public ResponseEntity<CartResponse> searchCart(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.searchCart(userId));
    }

    @Operation(summary = "수량 수정", description = "장바구니에 담긴 특정 메뉴의 수량을 수정합니다.")
    @PatchMapping("/cart/quantity")
    public void updateItemQuantity(@RequestBody UpdateQuantityRequest request) {
        cartService.updateItemQuantity(new UpdateQuantityCommand(
                request.getUserId(),
                request.getItemId(),
                request.getQuantity()
        ));
    }

    @Operation(summary = "메뉴 옵션 수정", description = "장바구니에 담긴 특정 메뉴의 옵션을 수정합니다.")
    @PatchMapping("/cart/option")
    public void updateItemOption(@RequestBody UpdateItemOptionRequest request) {
        cartService.updateItemOption(new UpdateItemOptionCommand(
                request.getUserId(),
                request.getItem()
        ));
    }

    @Operation(summary = "항목 삭제", description = "장바구니에서 특정 메뉴를 제거합니다.")
    @DeleteMapping("/cart")
    public void removeItem(@RequestBody AddItemRequest request) {
        cartService.removeItem(new RemoveItemCommand(
                request.getUserId(),
                request.getStoreId(),
                request.getItem()
        ));
    }

    @Operation(summary = "장바구니 비우기", description = "장바구니에 담긴 모든 메뉴를 삭제합니다.")
    @DeleteMapping("/cart/{userId}")
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
    }

    @ExceptionHandler(InvalidCartException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCart(InvalidCartException e) {
        return ResponseEntity.badRequest().body(Map.of(
                "message", e.getMessage(),
                "errors", e.getErrors()
        ));
    }
}
