package com.github.mingyu.fooddeliveryapi.domain.store.presentation;

import com.github.mingyu.fooddeliveryapi.domain.store.application.StoreService;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.dto.*;
import com.github.mingyu.fooddeliveryapi.store.domain.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Store API", description = "가게 관련 API")
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/store")
    @Operation(summary = "가게 생성", description = "새로운 가게를 등록합니다.")
    public ResponseEntity<StoreResponseDto> createStore(@RequestBody StoreCreateRequestDto request) {
        StoreResponseDto response = storeService.createStore(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/store/{storeId}")
    @Operation(summary = "가게 조회", description = "가게 ID로 단일 가게를 조회합니다.")
    public ResponseEntity<StoreResponseDto> getStoreById(@PathVariable Long storeId) {
        StoreResponseDto store = storeService.getStoreById(storeId);
        return ResponseEntity.ok(store);
    }

    @PutMapping("/store/{storeId}")
    @Operation(summary = "가게 수정", description = "가게 정보를 수정합니다.")
    public ResponseEntity<StoreResponseDto> updateStore(@PathVariable Long storeId, @RequestBody StoreUpdateRequestDto request) {
        StoreResponseDto updatedStore = storeService.updateStore(storeId, request);
        return ResponseEntity.ok(updatedStore);
    }

    @DeleteMapping("/store/{storeId}")
    @Operation(summary = "가게 삭제", description = "가게를 삭제합니다.")
    public ResponseEntity<Void> deleteStore(@PathVariable Long storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/store/search")
    @Operation(summary = "가게 목록 조회", description = "이름 또는 카테고리로 가게를 조회합니다.")
    public ResponseEntity<StoreListResponseDto> searchStores(@ModelAttribute StoreSearchCondition request) {
        StoreListResponseDto stores = storeService.searchStores(request);
        return ResponseEntity.ok(stores);
    }
}
