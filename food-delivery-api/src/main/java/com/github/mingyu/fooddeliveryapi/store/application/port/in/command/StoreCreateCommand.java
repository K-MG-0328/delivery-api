package com.github.mingyu.fooddeliveryapi.store.application.port.in.command;

import com.github.mingyu.fooddeliveryapi.store.domain.DeliveryTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreCreateCommand {
   private String name;
   private String category;
   private String address;
   private String phone;
   private Integer minDeliveryPrice;
   private Integer deliveryTip;
   private DeliveryTime deliveryTime;
   private String deliveryAreas;
}
