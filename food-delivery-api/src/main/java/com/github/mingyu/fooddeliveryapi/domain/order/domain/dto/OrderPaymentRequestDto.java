package com.github.mingyu.fooddeliveryapi.domain.order.domain.dto;

import com.github.mingyu.fooddeliveryapi.domain.order.domain.OrderState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "주문 결제 요청 DTO")
public class OrderPaymentRequestDto {

    @Schema(description = "주문 ID", example = "1001")
    private Long orderId;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "가게 ID", example = "10")
    private Long storeId;

    @Schema(description = "결제 방식", example = "CARD")
    private String paymentMethod;

    @Schema(description = "요청사항", example = "덜 맵게 해주세요")
    private String requests;

    @Schema(description = "주문 상태", example = "CREATED")
    private OrderState status;
}
