package com.github.mingyu.fooddeliveryapi.domain.order.presentation.dto;

import com.github.mingyu.fooddeliveryapi.domain.order.domain.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "주문 생성 요청 DTO")
public class OrderCreateRequest {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

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

    @Schema(description = "요청사항", example = "덜 맵게 해주세요")
    private String requests;
}
