package com.github.mingyu.fooddeliveryapi.store.adapter.in.web.request;

import com.github.mingyu.fooddeliveryapi.store.domain.DeliveryTime;
import com.github.mingyu.fooddeliveryapi.store.domain.StoreStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "가게 수정 요청 DTO")
@Getter
@AllArgsConstructor
public class StoreUpdateRequest {

    @Schema(description = "가게 id", example = "uuid")
    private String storeId;

    @Schema(description = "가게 이름", example = "김밥나라")
    private String name;

    @Schema(description = "카테고리", example = "한식")
    private String category;

    @Schema(description = "주소", example = "서울시 송파구")
    private String address;

    @Schema(description = "전화번호", example = "010-0000-0000")
    private String phone;

    @Schema(description = "최소 주문 금액", example = "12000")
    private int minDeliveryPrice;

    @Schema(description = "배달 팁", example = "2500")
    private int deliveryTip;

    @Schema(description = "최소/최대 배달 시간")
    private DeliveryTime deliveryTime;

    @Schema(description = "배달 지역", example = "강남구, 서초구")
    private String deliveryAreas;

    @Schema(description = "가게 상태", example = "ACTIVE")
    private StoreStatus status;
}

