package com.github.mingyu.fooddeliveryapi.domain.store.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "가게 생성 요청 DTO")
@Getter
@Setter
public class StoreCreateRequest {

    @Schema(description = "가게 이름", example = "김밥천국")
    private String name;

    @Schema(description = "카테고리", example = "한식")
    private String category;

    @Schema(description = "주소", example = "서울특별시 강남구")
    private String address;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;

    @Schema(description = "최소 주문 금액", example = "15000")
    private int minDeliveryPrice;

    @Schema(description = "배달 팁", example = "3000")
    private int deliveryTip;

    @Schema(description = "최소 배달 시간(분)", example = "20")
    private int minDeliveryTime;

    @Schema(description = "최대 배달 시간(분)", example = "45")
    private int maxDeliveryTime;

    @Schema(description = "배달 지역", example = "강남구, 서초구")
    private String deliveryAreas;
}
