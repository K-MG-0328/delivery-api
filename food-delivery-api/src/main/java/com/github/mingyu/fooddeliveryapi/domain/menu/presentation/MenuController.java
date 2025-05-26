package com.github.mingyu.fooddeliveryapi.domain.menu.presentation;

import com.github.mingyu.fooddeliveryapi.domain.menu.application.MenuMapper;
import com.github.mingyu.fooddeliveryapi.domain.menu.application.MenuService;
import com.github.mingyu.fooddeliveryapi.domain.menu.application.dto.MenuParam;
import com.github.mingyu.fooddeliveryapi.domain.menu.presentation.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Menu API", description = "메뉴 관련 API")
public class MenuController {

    private final MenuService menuService;
    private final MenuMapper menuMapper;

    @Operation(summary = "메뉴 추가", description = "새로운 메뉴를 추가합니다.")
    @PostMapping("/menu")
    public ResponseEntity<Void> addMenu(@RequestBody MenuCreateRequest request) {
        MenuParam menuParam = menuMapper.toMenuParam(request);
        menuService.addMenu(menuParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "메뉴 삭제", description = "특정 메뉴를 삭제합니다.")
    @DeleteMapping("/menu/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable String menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "메뉴 수정", description = "기존 메뉴 정보를 수정합니다.")
    @PutMapping("/menu/{menuId}")
    public ResponseEntity<Void> updateMenu(@PathVariable String menuId, @RequestBody MenuUpdateRequest request) {
        MenuParam menuParam = menuMapper.toMenuParam(request);
        menuService.updateMenu(menuId, menuParam);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "메뉴 조회", description = "특정 메뉴의 상세 정보를 조회합니다.")
    @GetMapping("/menu/{menuId}")
    public ResponseEntity<MenuResponse> getMenu(@PathVariable String menuId) {
        MenuParam param = menuService.getMenu(menuId);
        MenuResponse response = menuMapper.toMenuResponse(param);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "메뉴 목록 조회", description = "가게 메뉴 목록를 조회합니다.")
    @GetMapping("/menu/search")
    public ResponseEntity<MenuListResponse> searchMenus(@PathVariable String storeId) {
        List<MenuParam> menuParams = menuService.searchMenus(storeId);
        List<MenuResponse> menuResponses = menuMapper.toMenuResponses(menuParams);
        MenuListResponse response = new MenuListResponse();
        response.setMenus(menuResponses);
        return ResponseEntity.ok(response);
    }
}
