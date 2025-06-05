package com.github.mingyu.fooddeliveryapi.store.adapter.in.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "가게 목록 응답 DTO")
@AllArgsConstructor
public class StoreListResponse {

    @Schema(description = "가게 목록")
    private List<StoreResponse> stores  = new ArrayList<>();
}
