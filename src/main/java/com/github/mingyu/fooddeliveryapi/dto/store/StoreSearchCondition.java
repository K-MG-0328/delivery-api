package com.github.mingyu.fooddeliveryapi.dto.store;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "가게 검색 조건 DTO")
@Getter
@Setter
public class StoreSearchCondition {

    @Schema(description = "가게 이름", example = "김밥천국")
    private String name;

    @Schema(description = "카테고리", example = "한식")
    private String category;

    @Schema(description = "배달 지역", example = "강남구")
    private String deliveryAreas;
}
