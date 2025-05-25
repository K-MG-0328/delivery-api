package com.github.mingyu.fooddeliveryapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemOption {

    @JsonProperty("menuOptionId")
    private Long menuOptionId;

    @JsonProperty("optionName")
    private String optionName;

    @JsonProperty("price")
    private Integer price;
}
