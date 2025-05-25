package com.github.mingyu.fooddeliveryapi.domain.store.application;

import com.github.mingyu.fooddeliveryapi.domain.store.domain.Store;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.StoreStatus;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.StoreRepository;
import com.github.mingyu.fooddeliveryapi.domain.store.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreResponse createStore(StoreCreateRequest request) {
        Store store = storeMapper.toEntity(request);
        store.setStatus(StoreStatus.ACTIVE);
        storeRepository.save(store);
        StoreResponse responseDto = storeMapper.toDto(store);
        return responseDto;
    }

    public StoreResponse getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));
        return storeMapper.toDto(store);
    }

    public StoreResponse updateStore(Long storeId, StoreUpdateRequest request) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        storeMapper.updateFromDto(request, store);

        storeRepository.save(store);
        StoreResponse responseDto = storeMapper.toDto(store);
        return responseDto;
    }

    public void deleteStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        store.setStatus(StoreStatus.DELETED);
        storeRepository.save(store);
    }

    public StoreListResponse searchStores(StoreSearchCondition request) {
        String name = request.getName();
        String category = request.getCategory();
        String address = request.getDeliveryAreas();


        List<Store> stores = storeRepository.findByNameAndCategory(name, category, address);
        List<StoreResponse> storeDto = storeMapper.toDtoList(stores);

        StoreListResponse responseDto = new StoreListResponse();
        responseDto.setStores(storeDto);

        return responseDto;
    }
}
