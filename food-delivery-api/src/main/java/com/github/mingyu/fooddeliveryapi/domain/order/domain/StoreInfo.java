package com.github.mingyu.fooddeliveryapi.domain.order.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
public class StoreInfo {
    protected StoreInfo(){};

    private String storeId;
    private String storeName;
    private String storePhone;
    private String storeAddress;
}
