package com.github.mingyu.fooddeliveryapi.domain.cart.application;

import com.github.mingyu.fooddeliveryapi.domain.cart.application.dto.CartItemParam;
import com.github.mingyu.fooddeliveryapi.domain.cart.application.dto.CartParam;
import com.github.mingyu.fooddeliveryapi.domain.cart.application.dto.SingleCartParam;
import com.github.mingyu.fooddeliveryapi.domain.cart.domain.Cart;
import com.github.mingyu.fooddeliveryapi.domain.cart.presentation.dto.CartRequest;
import com.github.mingyu.fooddeliveryapi.domain.cart.presentation.dto.CartResponse;
import com.github.mingyu.fooddeliveryapi.domain.cart.presentation.dto.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartParam toCartParam(Cart cart);
    CartParam toCartParam(CartRequest request);
    SingleCartParam toCartParam(CartRequest request);
    CartItemParam toCartItemParam(Item item);


    CartResponse toCartResponse(CartParam cartParam);
}
