package com.github.mingyu.fooddeliveryapi.domain.store.presentation.dto;

import com.github.mingyu.fooddeliveryapi.domain.store.domain.DeliveryTime;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.StoreStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "가게 응답 DTO")
public class StoreResponse {

    @Schema(description = "가게 ID", example = "1")
    private String storeId;
    @Schema(description = "가게 이름", example = "김밥천국")
    private String name;
    @Schema(description = "음식 카테고리", example = "한식")
    private String category;
    @Schema(description = "가게 주소", example = "서울특별시 강남구")
    private String address;
    @Schema(description = "가게 전화번호", example = "010-1234-5678")
    private String phone;
    @Schema(description = "최소 주문 금액", example = "15000")
    private int minDeliveryPrice;
    @Schema(description = "배달 팁", example = "2000")
    private int deliveryTip;
    @Schema(description = "최소/최대 배달 시간")
    private DeliveryTime deliveryTime;
    @Schema(description = "가게 평점", example = "4.5")
    private double ratings;
    @Schema(description = "가게 상태", example = "ACTIVE")
    private StoreStatus status;
    @Schema(description = "배달 지역", example = "강남구, 서초구")
    private String deliveryAreas;

}
