package com.github.mingyu.fooddeliveryapi.menu.adapter.in;

import com.github.mingyu.fooddeliveryapi.menu.adapter.in.request.MenuCreateRequest;
import com.github.mingyu.fooddeliveryapi.menu.adapter.in.request.MenuUpdateRequest;
import com.github.mingyu.fooddeliveryapi.menu.adapter.in.response.MenuListResponse;
import com.github.mingyu.fooddeliveryapi.menu.adapter.in.response.MenuResponse;
import com.github.mingyu.fooddeliveryapi.menu.application.port.in.command.MenuCreateCommand;
import com.github.mingyu.fooddeliveryapi.menu.application.port.in.command.MenuOptionCreateCommand;
import com.github.mingyu.fooddeliveryapi.menu.application.port.in.command.MenuOptionUpdateCommand;
import com.github.mingyu.fooddeliveryapi.menu.application.port.in.command.MenuUpdateCommand;
import com.github.mingyu.fooddeliveryapi.menu.application.service.MenuService;
import com.github.mingyu.fooddeliveryapi.menu.domain.exception.InvalidMenuException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "Menu API", description = "메뉴 관련 API")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "메뉴 추가", description = "새로운 메뉴를 추가합니다.")
    @PostMapping("/menu")
    public ResponseEntity<Void> addMenu(@RequestBody MenuCreateRequest request) {

        List<MenuOptionCreateCommand> options = request.getOptions() != null
                ? request.getOptions().stream()
                    .map(option -> new MenuOptionCreateCommand(option.getOptionName(), option.getPrice()))
                    .toList()
                : new ArrayList<>();

        menuService.addMenu(new MenuCreateCommand(
                request.getStoreId(),
                request.getName(),
                request.getPrice(),
                options
        ));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "메뉴 삭제", description = "특정 메뉴를 삭제합니다.")
    @DeleteMapping("/menu/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable String menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "메뉴 수정", description = "기존 메뉴 정보를 수정합니다.")
    @PutMapping("/menu/{menuId}")
    public ResponseEntity<Void> updateMenu(@RequestBody MenuUpdateRequest request) {

        List<MenuOptionUpdateCommand> options = request.getOptions() != null
                ? request.getOptions().stream()
                .map(option -> new MenuOptionUpdateCommand(option.getMenuOptionId(),
                        option.getOptionName(),
                        option.getPrice(),
                        option.getStatus()))
                .toList()
                : new ArrayList<>();

        menuService.updateMenu(new MenuUpdateCommand(
                request.getMenuId(),
                request.getStoreId(),
                request.getName(),
                request.getPrice(),
                request.getStatus(),
                options
        ));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "메뉴 조회", description = "특정 메뉴의 상세 정보를 조회합니다.")
    @GetMapping("/menu/{menuId}")
    public ResponseEntity<MenuResponse> searchMenu(@PathVariable String menuId) {
        return ResponseEntity.ok(menuService.searchMenu(menuId));
    }

    @Operation(summary = "메뉴 목록 조회", description = "가게 메뉴 목록를 조회합니다.")
    @GetMapping("/menu/search")
    public ResponseEntity<MenuListResponse> searchMenus(@PathVariable String storeId) {
        return ResponseEntity.ok(menuService.searchMenus(storeId));
    }

    @ExceptionHandler(InvalidMenuException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidMenu(InvalidMenuException e) {
        return ResponseEntity.badRequest().body(Map.of(
                "message", e.getMessage(),
                "errors", e.getErrors()
        ));
    }
}
