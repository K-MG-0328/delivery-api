package com.github.mingyu.fooddeliveryapi.domain.order.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Embeddable
@AllArgsConstructor
public class OrderItemOption {

    protected OrderItemOption(){};

    private String itemId;
    private String optionName;
    private Integer price;
}
