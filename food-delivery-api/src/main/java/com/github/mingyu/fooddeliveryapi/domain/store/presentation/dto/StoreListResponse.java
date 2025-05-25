package com.github.mingyu.fooddeliveryapi.domain.store.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(description = "가게 목록 응답 DTO")
public class StoreListResponse {

    @Schema(description = "가게 목록")
    private List<StoreResponse> stores  = new ArrayList<>();
}
