package com.github.mingyu.fooddeliveryapi.domain.order.presentation;

import com.github.mingyu.fooddeliveryapi.domain.order.domain.dto.*;
import com.github.mingyu.fooddeliveryapi.order.domain.dto.*;
import com.github.mingyu.fooddeliveryapi.domain.order.application.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order API", description = "주문 관련 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "장바구니를 기반으로 새로운 주문을 생성합니다.")
    @PostMapping("/order")
    public ResponseEntity<OrderCreateResponseDto> createOrder(@RequestBody OrderCreateRequestDto request) {
        OrderCreateResponseDto order = orderService.createOrder(request);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "주문 결제 처리", description = "주문을 결제 처리합니다.")
    @PostMapping("/order/pay")
    public ResponseEntity<Void> payOrder(@RequestBody OrderPaymentRequestDto request) {
        orderService.payOrder(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 주문 목록 조회", description = "사용자의 모든 주문 목록을 반환합니다.")
    @GetMapping("/order")
    public ResponseEntity<OrderListResponseDto> getUserOrders(@RequestParam Long userId) {
        OrderListResponseDto orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "주문 상세 조회", description = "주문의 상세 정보를 반환합니다.")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDetailResponseDto> getOrder(@PathVariable Long orderId) {
        OrderDetailResponseDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }


    @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}