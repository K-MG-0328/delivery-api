package com.github.mingyu.fooddeliveryapi.domain.order.presentation;

import com.github.mingyu.fooddeliveryapi.domain.order.application.OrderMapper;
import com.github.mingyu.fooddeliveryapi.domain.order.application.OrderService;
import com.github.mingyu.fooddeliveryapi.domain.order.application.dto.OrderParam;
import com.github.mingyu.fooddeliveryapi.domain.order.presentation.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order API", description = "주문 관련 API")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다.")
    @PostMapping("/order")
    public ResponseEntity<Void> createOrder(@RequestBody OrderCreateRequest request) {
        OrderParam param = orderMapper.toOrderParam(request);
        orderService.createOrder(param);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "사용자 주문 목록 조회", description = "사용자의 모든 주문 목록을 반환합니다.")
    @GetMapping("/order")
    public ResponseEntity<OrderListResponse> getUserOrders(@RequestParam Long userId) {
        List<OrderParam> userOrders = orderService.getUserOrders(userId);
        List<OrderDetailResponse> responses = orderMapper.toOrderDetailResponses(userOrders);
        OrderListResponse responseWrapper = new OrderListResponse(responses);
        return ResponseEntity.ok(responseWrapper);
    }

    @Operation(summary = "주문 상세 조회", description = "주문의 상세 정보를 반환합니다.")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrder(@PathVariable String orderId) {
        OrderParam order = orderService.getOrder(orderId);
        OrderDetailResponse response = orderMapper.toOrderDetailResponse(order);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable String orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}