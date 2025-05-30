package com.github.mingyu.fooddeliveryapi.domain.store.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class DeliveryTime {

    protected DeliveryTime() {};

    private Integer minMinutes;
    private Integer maxMinutes;
}
