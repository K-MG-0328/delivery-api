package com.github.mingyu.fooddeliveryapi.domain.store.application.dto;

import com.github.mingyu.fooddeliveryapi.domain.store.domain.DeliveryTime;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreParam {
   private String storeId;
   private String name;
   private String category;
   private String address;
   private String phone;
   private Integer minDeliveryPrice;
   private Integer deliveryTip;
   private DeliveryTime deliveryTime;
   private Double ratings;
   private StoreStatus status;
   private String deliveryAreas;
}
