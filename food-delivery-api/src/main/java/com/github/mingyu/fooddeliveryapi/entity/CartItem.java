package com.github.mingyu.fooddeliveryapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @JsonProperty("menuId")
    private Long menuId;

    @JsonProperty("menuOptionIds")
    private List<Long> menuOptionIds;

    @JsonProperty("quantity")
    private int quantity;

}