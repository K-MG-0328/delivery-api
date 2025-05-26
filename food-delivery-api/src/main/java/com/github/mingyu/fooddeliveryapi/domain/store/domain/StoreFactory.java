package com.github.mingyu.fooddeliveryapi.domain.store.domain;

import com.github.mingyu.fooddeliveryapi.common.util.IdGenerator;
import com.github.mingyu.fooddeliveryapi.domain.store.application.dto.StoreParam;

public class StoreFactory {

    private StoreFactory() {}

    public static Store createStore(StoreParam param) {
        Store store = createStore(param.getName(),
                param.getCategory(),
                param.getAddress(),
                param.getPhone(),
                param.getMinDeliveryPrice(),
                param.getDeliveryTip(),
                param.getDeliveryTime(),
                param.getRatings(),
                param.getStatus(),
                param.getDeliveryAreas()
        );
        return store;
    }

    public static Store createStore( String name,
                                   String category,
                                   String address,
                                   String phone,
                                   Integer minDeliveryPrice,
                                   Integer deliveryTip,
                                   DeliveryTime deliveryTime,
                                   Double ratings,
                                   StoreStatus status,
                                   String deliveryAreas ) {

        StoreValidator.validate(name, category, address, phone, minDeliveryPrice, deliveryTip, deliveryTime, ratings, status, deliveryAreas);
        String storeId = IdGenerator.uuid();
        return new Store(storeId, name, category, address, phone, minDeliveryPrice, deliveryTip, deliveryTime, ratings, status, deliveryAreas);
    }


}
