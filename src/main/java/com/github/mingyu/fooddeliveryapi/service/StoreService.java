package com.github.mingyu.fooddeliveryapi.service;

import com.github.mingyu.fooddeliveryapi.dto.store.*;
import com.github.mingyu.fooddeliveryapi.entity.Store;
import com.github.mingyu.fooddeliveryapi.enums.StoreStatus;
import com.github.mingyu.fooddeliveryapi.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;

    public StoreResponseDto createStore(StoreCreateRequestDto request) {
        Store store = modelMapper.map(request, Store.class);
        store.setStatus(StoreStatus.ACTIVE);
        storeRepository.save(store);
        StoreResponseDto responseDto = modelMapper.map(store, StoreResponseDto.class);
        return responseDto;
    }

    public StoreResponseDto getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));
        return modelMapper.map(store, StoreResponseDto.class);
    }

    public StoreResponseDto updateStore(Long storeId, StoreUpdateRequestDto request) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        store.setName(request.getName());
        store.setAddress(request.getAddress());
        store.setPhone(request.getPhone());
        store.setMinDeliveryPrice(request.getMinDeliveryPrice());
        store.setStatus(request.getStatus());

        storeRepository.save(store);
        StoreResponseDto responseDto = modelMapper.map(store, StoreResponseDto.class);
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
        List<StoreResponseDto> storeDto = stores.stream().map(s -> modelMapper.map(s, StoreResponseDto.class)).collect(Collectors.toList());

        StoreListResponseDto responseDto = new StoreListResponseDto();
        responseDto.setStores(storeDto);

        return responseDto;
    }
}
