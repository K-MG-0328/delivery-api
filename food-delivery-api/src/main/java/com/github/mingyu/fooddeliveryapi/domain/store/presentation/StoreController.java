package com.github.mingyu.fooddeliveryapi.domain.store.presentation;

import com.github.mingyu.fooddeliveryapi.domain.store.application.StoreMapper;
import com.github.mingyu.fooddeliveryapi.domain.store.application.StoreService;
import com.github.mingyu.fooddeliveryapi.domain.store.application.dto.StoreParam;
import com.github.mingyu.fooddeliveryapi.domain.store.presentation.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Store API", description = "가게 관련 API")
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final StoreMapper storeMapper;

    @PostMapping("/store")
    @Operation(summary = "가게 생성", description = "새로운 가게를 등록합니다.")
    public ResponseEntity<Void> createStore(@RequestBody StoreCreateRequest request) {
        StoreParam storeParam = storeMapper.toStoreParam(request);
        storeService.createStore(storeParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/store/{storeId}")
    @Operation(summary = "가게 조회", description = "가게 ID로 단일 가게를 조회합니다.")
    public ResponseEntity<StoreResponse> getStore(@PathVariable String storeId) {
        StoreParam storeParam = storeService.getStore(storeId);
        StoreResponse response = storeMapper.toStoreResponse(storeParam);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/store/{storeId}")
    @Operation(summary = "가게 수정", description = "가게 정보를 수정합니다.")
    public ResponseEntity<StoreResponse> updateStore(@PathVariable String storeId, @RequestBody StoreUpdateRequest request) {
        StoreParam storeParam = storeMapper.toStoreParam(request);
        storeService.updateStore(storeId, storeParam);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/store/{storeId}")
    @Operation(summary = "가게 삭제", description = "가게를 삭제합니다.")
    public ResponseEntity<Void> deleteStore(@PathVariable String storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/store/search")
    @Operation(summary = "가게 목록 조회", description = "이름 또는 카테고리로 가게를 조회합니다.")
    public ResponseEntity<StoreListResponse> searchStores(@ModelAttribute StoreSearchCondition request) {
        StoreParam storeParam = storeMapper.toStoreParam(request);
        List<StoreParam> storeParams = storeService.searchStores(storeParam);
        List<StoreResponse> storeResponse = storeMapper.toStoreResponse(storeParams);
        StoreListResponse response = new StoreListResponse(storeResponse);
        return ResponseEntity.ok(response);
    }
}
