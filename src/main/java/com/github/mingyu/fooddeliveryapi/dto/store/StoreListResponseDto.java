package com.github.mingyu.fooddeliveryapi.dto.store;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(description = "가게 목록 응답 DTO")
public class StoreListResponseDto {

    @Schema(description = "가게 목록")
    private List<StoreResponseDto> stores  = new ArrayList<>();
}
