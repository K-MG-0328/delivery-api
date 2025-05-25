package com.github.mingyu.fooddeliveryapi.dto.order;

import com.github.mingyu.fooddeliveryapi.dto.store.StoreResponseDto;
import com.github.mingyu.fooddeliveryapi.dto.user.UserResponseDto;
import com.github.mingyu.fooddeliveryapi.enums.OrderState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "주문 생성 응답 DTO")
public class OrderCreateResponseDto {

    @Schema(description = "주문 ID", example = "1001")
    private Long orderId;

    @Schema(description = "사용자")
    private UserResponseDto user;

    @Schema(description = "가게")
    private StoreResponseDto store;

    @Schema(description = "주문 상태", example = "CREATED")
    private OrderState orderState;

    @Schema(description = "총 금액", example = "26000")
    private int totalPrice;

    @Schema(description = "주문 생성일시", example = "2024-05-01T14:35:00")
    private LocalDateTime createdDate;

}
