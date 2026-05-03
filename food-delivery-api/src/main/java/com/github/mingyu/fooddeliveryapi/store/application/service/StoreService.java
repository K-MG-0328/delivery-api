package com.github.mingyu.fooddeliveryapi.store.application.service;

import com.github.mingyu.fooddeliveryapi.store.adapter.in.web.response.StoreListResponse;
import com.github.mingyu.fooddeliveryapi.store.adapter.in.web.response.StoreResponse;
import com.github.mingyu.fooddeliveryapi.store.application.port.in.CloseStoreUseCase;
import com.github.mingyu.fooddeliveryapi.store.application.port.in.CreateStoreUseCase;
import com.github.mingyu.fooddeliveryapi.store.application.port.in.SearchStoreUseCase;
import com.github.mingyu.fooddeliveryapi.store.application.port.in.UpdateStoreUseCase;
import com.github.mingyu.fooddeliveryapi.store.application.port.in.command.StoreCreateCommand;
import com.github.mingyu.fooddeliveryapi.store.application.port.in.command.StoreSearchCommand;
import com.github.mingyu.fooddeliveryapi.store.application.port.in.command.StoreUpdateCommand;
import com.github.mingyu.fooddeliveryapi.store.application.port.out.StoreRepositoryPort;
import com.github.mingyu.fooddeliveryapi.store.domain.Store;
import com.github.mingyu.fooddeliveryapi.store.domain.StoreFactory;
import com.github.mingyu.fooddeliveryapi.store.domain.StoreStatus;
import com.github.mingyu.fooddeliveryapi.store.domain.StoreValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService implements CreateStoreUseCase, UpdateStoreUseCase, CloseStoreUseCase, SearchStoreUseCase {

    private final StoreRepositoryPort storeRepositoryPort;

    @Override
    @Transactional
    public void createStore(StoreCreateCommand command) {
        Store store = StoreFactory.createStore(command);
        StoreValidator.validate(store);
        storeRepositoryPort.save(store);
    }

    @Override
    @Transactional
    public void updateStore(StoreUpdateCommand command) {
        Store store = getStore(command.getStoreId());
        store.update(
                command.getName(),
                command.getCategory(),
                command.getAddress(),
                command.getPhone(),
                command.getMinDeliveryPrice(),
                command.getDeliveryTip(),
                command.getDeliveryTime(),
                command.getDeliveryAreas(),
                command.getStatus()
        );
        StoreValidator.validate(store);
        storeRepositoryPort.save(store);
    }

    @Override
    @Transactional
    public void closeStore(String storeId) {
        Store store = getStore(storeId);
        store.changeStatus(StoreStatus.CLOSED);
        storeRepositoryPort.save(store);
    }

    @Override
    @Transactional(readOnly = true)
    public StoreResponse searchStore(String storeId) {
        Store store = getStore(storeId);
        return new StoreResponse(
                store.getStoreId(),
                store.getName(),
                store.getCategory(),
                store.getAddress(),
                store.getPhone(),
                store.getMinDeliveryPrice(),
                store.getDeliveryTip(),
                store.getDeliveryTime(),
                store.getRatings(),
                store.getStatus(),
                store.getDeliveryAreas()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public StoreListResponse searchStores(StoreSearchCommand command) {
        List<Store> stores = storeRepositoryPort.findByNameAndCategory(command.getName(),
                command.getCategory(),
                command.getDeliveryAreas());

        List<StoreResponse> responses = new ArrayList<>();
        for (Store store : stores) {
            responses.add(new StoreResponse(
                    store.getStoreId(),
                    store.getName(),
                    store.getCategory(),
                    store.getAddress(),
                    store.getPhone(),
                    store.getMinDeliveryPrice(),
                    store.getDeliveryTip(),
                    store.getDeliveryTime(),
                    store.getRatings(),
                    store.getStatus(),
                    store.getDeliveryAreas()
            ));
        }

        return new StoreListResponse(responses);
    }

    @Transactional(readOnly = true)
    public Store getStore(String storeId) {
        Store store = storeRepositoryPort.findById(storeId);
        if(store.getStatus() == StoreStatus.CLOSED) {
            throw new RuntimeException("가게를 찾을 수 없습니다.");
        }
        return store;
    }
}
