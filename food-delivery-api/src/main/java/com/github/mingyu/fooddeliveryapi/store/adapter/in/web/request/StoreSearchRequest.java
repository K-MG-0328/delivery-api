package com.github.mingyu.fooddeliveryapi.store.adapter.in.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "가게 검색 조건 DTO")
@Getter
@AllArgsConstructor
public class StoreSearchRequest {

    @Schema(description = "가게 이름", example = "김밥천국")
    private String name;

    @Schema(description = "카테고리", example = "한식")
    private String category;

    @Schema(description = "배달 지역", example = "강남구")
    private String deliveryAreas;
}
