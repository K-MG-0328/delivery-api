package com.github.mingyu.fooddeliveryapi.store.adapter.in.web;

import com.github.mingyu.fooddeliveryapi.store.adapter.in.web.request.StoreCreateRequest;
import com.github.mingyu.fooddeliveryapi.store.adapter.in.web.request.StoreSearchRequest;
import com.github.mingyu.fooddeliveryapi.store.adapter.in.web.request.StoreUpdateRequest;
import com.github.mingyu.fooddeliveryapi.store.adapter.in.web.response.StoreListResponse;
import com.github.mingyu.fooddeliveryapi.store.adapter.in.web.response.StoreResponse;
import com.github.mingyu.fooddeliveryapi.store.application.port.in.command.StoreCreateCommand;
import com.github.mingyu.fooddeliveryapi.store.application.port.in.command.StoreSearchCommand;
import com.github.mingyu.fooddeliveryapi.store.application.port.in.command.StoreUpdateCommand;
import com.github.mingyu.fooddeliveryapi.store.application.service.StoreService;
import com.github.mingyu.fooddeliveryapi.store.domain.exception.InvalidStoreException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Store API", description = "가게 관련 API")
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/store")
    @Operation(summary = "가게 생성", description = "새로운 가게를 등록합니다.")
    public ResponseEntity<Void> createStore(@RequestBody StoreCreateRequest request) {
        storeService.createStore(new StoreCreateCommand(
                request.getName(),
                request.getCategory(),
                request.getAddress(),
                request.getPhone(),
                request.getMinDeliveryPrice(),
                request.getDeliveryTip(),
                request.getDeliveryTime(),
                request.getDeliveryAreas()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/store/{storeId}")
    @Operation(summary = "가게 수정", description = "가게 정보를 수정합니다.")
    public ResponseEntity<Void> updateStore(@RequestBody StoreUpdateRequest request) {
        storeService.updateStore(new StoreUpdateCommand(
                request.getStoreId(),
                request.getName(),
                request.getCategory(),
                request.getAddress(),
                request.getPhone(),
                request.getMinDeliveryPrice(),
                request.getDeliveryTip(),
                request.getDeliveryTime(),
                request.getDeliveryAreas(),
                request.getStatus()
        ));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/store/{storeId}")
    @Operation(summary = "가게 삭제", description = "가게를 삭제합니다.")
    public ResponseEntity<Void> closeStore(@PathVariable String storeId) {
        storeService.closeStore(storeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/store/{storeId}")
    @Operation(summary = "가게 조회", description = "가게 ID로 단일 가게를 조회합니다.")
    public ResponseEntity<StoreResponse> searchStore(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.searchStore(storeId));
    }

    @GetMapping("/store/search")
    @Operation(summary = "가게 목록 조회", description = "이름 또는 카테고리로 가게를 조회합니다.")
    public ResponseEntity<StoreListResponse> searchStores(@ModelAttribute StoreSearchRequest request) {

        StoreListResponse response = storeService.searchStores(new StoreSearchCommand(request.getName(), request.getCategory(), request.getDeliveryAreas()));

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(InvalidStoreException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidStore(InvalidStoreException e) {
        return ResponseEntity.badRequest().body(Map.of(
                "message", e.getMessage(),
                "errors", e.getErrors()
        ));
    }
}
