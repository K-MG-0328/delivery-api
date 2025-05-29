package com.github.mingyu.fooddeliveryapi.domain.cart.application;

import com.github.mingyu.fooddeliveryapi.domain.cart.presentation.dto.CartItemResponse;
import com.github.mingyu.fooddeliveryapi.domain.cart.domain.CartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem toEntity(CartItem dto);
    List<CartItemResponse> toDtos(List<CartItem> entity);
}
