package com.github.mingyu.fooddeliveryapi.store.domain;

import com.github.mingyu.fooddeliveryapi.common.util.IdCreator;
import com.github.mingyu.fooddeliveryapi.store.application.port.in.command.StoreCreateCommand;

public class StoreFactory {

    private StoreFactory() {}

    public static Store createStore(StoreCreateCommand param) {
        Store store = createStore(param.getName(),
                param.getCategory(),
                param.getAddress(),
                param.getPhone(),
                param.getMinDeliveryPrice(),
                param.getDeliveryTip(),
                param.getDeliveryTime(),
                0.0,
                StoreStatus.OPEN,
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
        String storeId = IdCreator.randomUuid();
        return new Store(storeId, name, category, address, phone, minDeliveryPrice, deliveryTip, deliveryTime, ratings, status, deliveryAreas);
    }


}
