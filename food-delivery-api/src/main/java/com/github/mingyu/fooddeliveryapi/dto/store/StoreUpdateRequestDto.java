package com.github.mingyu.fooddeliveryapi.dto.store;

import com.github.mingyu.fooddeliveryapi.enums.StoreStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "가게 수정 요청 DTO")
@Getter
@Setter
public class StoreUpdateRequestDto {

    @Schema(description = "가게 이름", example = "김밥나라")
    private String name;

    @Schema(description = "주소", example = "서울시 송파구")
    private String address;

    @Schema(description = "전화번호", example = "010-0000-0000")
    private String phone;

    @Schema(description = "최소 주문 금액", example = "12000")
    private int minDeliveryPrice;

    @Schema(description = "배달 팁", example = "2500")
    private int deliveryTip;

    @Schema(description = "가게 상태", example = "ACTIVE")
    private StoreStatus status;
}

