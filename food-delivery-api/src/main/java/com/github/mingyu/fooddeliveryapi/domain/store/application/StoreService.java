package com.github.mingyu.fooddeliveryapi.domain.store.application;

import com.github.mingyu.fooddeliveryapi.domain.store.domain.Store;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.StoreStatus;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.StoreRepository;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.dto.*;
import com.github.mingyu.fooddeliveryapi.store.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreResponseDto createStore(StoreCreateRequestDto request) {
        Store store = storeMapper.toEntity(request);
        store.setStatus(StoreStatus.ACTIVE);
        storeRepository.save(store);
        StoreResponseDto responseDto = storeMapper.toDto(store);
        return responseDto;
    }

    public StoreResponseDto getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));
        return storeMapper.toDto(store);
    }

    public StoreResponseDto updateStore(Long storeId, StoreUpdateRequestDto request) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        storeMapper.updateFromDto(request, store);

        storeRepository.save(store);
        StoreResponseDto responseDto = storeMapper.toDto(store);
        return responseDto;
    }

    public void deleteStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        store.setStatus(StoreStatus.DELETED);
        storeRepository.save(store);
    }

    public StoreListResponseDto searchStores(StoreSearchCondition request) {
        String name = request.getName();
        String category = request.getCategory();
        String address = request.getDeliveryAreas();


        List<Store> stores = storeRepository.findByNameAndCategory(name, category, address);
        List<StoreResponseDto> storeDto = storeMapper.toDtoList(stores);

        StoreListResponseDto responseDto = new StoreListResponseDto();
        responseDto.setStores(storeDto);

        return responseDto;
    }
}
