package com.github.mingyu.fooddeliveryapi.controller;

import com.github.mingyu.fooddeliveryapi.dto.store.StoreRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.store.StoreResponseDto;
import com.github.mingyu.fooddeliveryapi.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // 가게 생성
    @PostMapping("/store")
    public ResponseEntity<StoreResponseDto> createStore(@RequestBody StoreRequestDto request) {
        StoreResponseDto response = storeService.createStore(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

   // 가게 단일 조회
    @GetMapping("/store/{storeId}")
    public ResponseEntity<StoreResponseDto> getStoreById(@PathVariable Long storeId) {
        StoreResponseDto store = storeService.getStoreById(storeId);
        return ResponseEntity.ok(store);
    }

    // 가게 수정
    @PutMapping("/store/{storeId}")
    public ResponseEntity<StoreResponseDto> updateStore(@PathVariable Long storeId, @RequestBody StoreRequestDto request) {
        StoreResponseDto updatedStore = storeService.updateStore(storeId, request);
        return ResponseEntity.ok(updatedStore);
    }

    // 가게 삭제
    @DeleteMapping("/store/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.noContent().build();
    }

    // 가게 검색 (이름 또는 카테고리)
    @GetMapping("/store/search")
    public ResponseEntity<StoreResponseDto> searchStores(@ModelAttribute StoreRequestDto request) {
        StoreResponseDto stores = storeService.searchStores(request);
        return ResponseEntity.ok(stores);
    }
}
