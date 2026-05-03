package com.github.mingyu.fooddeliveryapi.store.adapter.out;

import com.github.mingyu.fooddeliveryapi.common.PersistentAdapter;
import com.github.mingyu.fooddeliveryapi.store.adapter.out.persistence.StoreRepository;
import com.github.mingyu.fooddeliveryapi.store.application.port.out.StoreRepositoryPort;
import com.github.mingyu.fooddeliveryapi.store.domain.Store;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistentAdapter
@RequiredArgsConstructor
public class StoreRepositoryAdapter implements StoreRepositoryPort {

    private final StoreRepository storeRepository;

    @Override
    public void save(Store store) {
        storeRepository.save(store);
    }

    @Override
    public List<Store> findByNameAndCategory(String name, String category, String deliveryAreas) {
        return storeRepository.findByNameAndCategory(name, category, deliveryAreas);
    }

    @Override
    public Store findById(String storeId) {
        return storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));
    }
}
