package com.github.mingyu.fooddeliveryapi.controller;


import com.github.mingyu.fooddeliveryapi.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "Delivery", description = "배송 관련 API")
public class RiderDeliveryController {

    private final DeliveryService deliveryService;

    @Operation(summary = "배송 상태 변경", description = "라이더가 배송 상태를 변경합니다")
    @PostMapping("/delivery/{orderId}/status")
    public void updateStatus(@PathVariable Long orderId, @RequestParam String status) {
        deliveryService.sendStatusUpdate(orderId, status);
    }

    @Operation(summary = "배송 완료 처리", description = "배송 완료 상태로 변경합니다.")
    @PostMapping("/delivery/{orderId}/complete")
    public void completeDelivery(@PathVariable Long orderId) {
        deliveryService.completeDelivery(orderId);
    }

    @Operation(summary = "배송 취소 처리", description = "배송을 취소하고 상태를 변경합니다.")
    @PostMapping("/delivery/{orderId}/cancel")
    public void cancelDelivery(@PathVariable Long orderId) {
        deliveryService.cancelDelivery(orderId);
    }
}
