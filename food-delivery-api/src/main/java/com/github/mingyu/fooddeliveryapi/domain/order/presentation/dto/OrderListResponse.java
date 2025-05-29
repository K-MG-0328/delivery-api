package com.github.mingyu.fooddeliveryapi.domain.order.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "주문 목록 응답 DTO")
public class OrderListResponse {

    @Schema(description = "주문 목록")
    private List<OrderDetailResponse> orders;
}