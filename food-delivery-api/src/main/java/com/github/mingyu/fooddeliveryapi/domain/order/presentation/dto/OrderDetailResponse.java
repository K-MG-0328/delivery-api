package com.github.mingyu.fooddeliveryapi.domain.order.presentation.dto;

import com.github.mingyu.fooddeliveryapi.domain.order.domain.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "주문 상세 응답 DTO")
public class OrderDetailResponse {

    @Schema(description = "주문 ID", example = "1001")
    private Long orderId;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String userName;

    @Schema(description = "가게 ID", example = "10")
    private String storeId;

    @Schema(description = "가게 이름", example = "김밥천국")
    private String storeName;

    @Schema(description = "가게 번호", example = "01011112222")
    private String storePhone;

    @Schema(description = "가게 주소", example = "01011112222")
    private String storeAddress;

    @Schema(description = "결제 방식", example = "CARD")
    private PaymentMethod paymentMethod;

    @Schema(description = "총 주문 금액", example = "23000")
    private Integer totalPrice;

    @Schema(description = "요청사항", example = "덜 맵게 해주세요")
    private String requests;

    @Schema(description = "주문 상태", example = "CREATED")
    private String status;

    @Schema(description = "주문 생성일시", example = "2024-06-01T15:00:00")
    private LocalDateTime createdDate;

    @Schema(description = "주문 항목 목록")
    private List<OrderItemResponse> items;
}
