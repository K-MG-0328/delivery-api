package com.github.mingyu.fooddeliveryapi.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "주문 생성 요청 DTO")
public class OrderCreateRequestDto {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "가게 ID", example = "10")
    private Long storeId;
}
