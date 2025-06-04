package com.github.mingyu.fooddeliveryapi.menu.adapter.in.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Schema(description = "메뉴 생성 요청 DTO")
@Getter
@AllArgsConstructor
public class MenuCreateRequest {
    @Schema(description = "가게 ID", example = "10")
    private String storeId;
    @Schema(description = "메뉴 이름", example = "불고기 덮밥")
    private String name;
    @Schema(description = "메뉴 가격", example = "8500")
    private Integer price;
    @Schema(description = "메뉴 옵션 목록")
    private List<MenuOptionCreateRequest> options;
}
