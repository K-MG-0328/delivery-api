package com.github.mingyu.fooddeliveryapi.service;

import com.github.mingyu.fooddeliveryapi.dto.store.StoreRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.store.StoreResponseDto;
import com.github.mingyu.fooddeliveryapi.entity.Store;
import com.github.mingyu.fooddeliveryapi.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;

    public StoreResponseDto createStore(StoreRequestDto request) {
        Store store = modelMapper.map(request, Store.class);
        storeRepository.save(store);
        StoreResponseDto responseDto = modelMapper.map(store, StoreResponseDto.class);
        return responseDto;
    }

    public StoreResponseDto getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));
        return modelMapper.map(store, StoreResponseDto.class);
    }

    public StoreResponseDto updateStore(Long storeId, StoreRequestDto request) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        store.setName(request.getName());
        store.setCategory(request.getCategory());
        store.setAddress(request.getAddress());
        store.setPhone(request.getPhone());
        store.setMinDeliveryPrice(request.getMinDeliveryPrice());
        store.setStatus(request.getStatus());
        store.setDeliveryAreas(request.getDeliveryAreas());

        storeRepository.save(store);
        StoreResponseDto responseDto = modelMapper.map(store, StoreResponseDto.class);
        return responseDto;
    }

    public void deleteStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        store.setStatus("DELETED");
        storeRepository.save(store);
    }

    public StoreResponseDto searchStores(StoreRequestDto request) {
        String name = request.getName();
        String category = request.getCategory();
        String address = request.getAddress();

        StoreResponseDto responseDto = new StoreResponseDto();
        responseDto.setStores(storeRepository.findByNameAndCategory(name, category, address));

        return responseDto;
    }
}
