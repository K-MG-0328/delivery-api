package com.github.mingyu.fooddeliveryapi.domain.store.application;

import com.github.mingyu.fooddeliveryapi.domain.store.application.dto.StoreParam;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.Store;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.StoreFactory;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.StoreRepository;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.StoreStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    @Transactional
    public void createStore(StoreParam param) {
        Store store = StoreFactory.createStore(param);
        storeRepository.save(store);
    }

    @Transactional(readOnly = true)
    public StoreParam getStore(String storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));
        if(store.getStatus() == StoreStatus.DELETED) {
            throw new RuntimeException("가게를 찾을 수 없습니다.");
        }
        StoreParam storeParam = storeMapper.toStoreParam(store);
        return storeParam;
    }

    @Transactional
    public void updateStore(String storeId, StoreParam param) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));
        if(store.getStatus() == StoreStatus.DELETED) {
            throw new RuntimeException("가게를 찾을 수 없습니다.");
        }
        store.update(param);
        storeRepository.save(store);
    }

    @Transactional
    public void deleteStore(String storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        store.changeStatus(StoreStatus.DELETED);
        storeRepository.save(store);
    }

    @Transactional(readOnly = true)
    public List<StoreParam> searchStores(StoreParam param) {

        String name = param.getName();
        String category = param.getCategory();
        String address = param.getDeliveryAreas();

        List<Store> stores = storeRepository.findByNameAndCategory(name, category, address);
        List<StoreParam> storeParams = storeMapper.toStoreParam(stores);

        return storeParams;
    }
}
