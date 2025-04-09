package com.github.mingyu.fooddeliveryapi.controller;

import com.github.mingyu.fooddeliveryapi.dto.menu.MenuCreateRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.menu.MenuUpdateRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.menu.MenuResponseDto;
import com.github.mingyu.fooddeliveryapi.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@Tag(name = "Menu API", description = "메뉴 관련 API")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "메뉴 추가", description = "새로운 메뉴를 추가합니다.")
    @PostMapping("/menu")
    public ResponseEntity<Void> addMenu(@RequestBody MenuCreateRequestDto request) {
        menuService.addMenu(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "메뉴 삭제", description = "특정 메뉴를 삭제합니다.")
    @DeleteMapping("/menu/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "메뉴 수정", description = "기존 메뉴 정보를 수정합니다.")
    @PutMapping("/menu/{menuId}")
    public ResponseEntity<Void> updateMenu(@PathVariable Long menuId, @RequestBody MenuUpdateRequestDto request) {
        menuService.updateMenu(menuId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "메뉴 조회", description = "특정 메뉴의 상세 정보를 조회합니다.")
    @GetMapping("/menu/{menuId}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.getMenu(menuId));
    }
}
