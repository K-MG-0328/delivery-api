package com.github.mingyu.fooddeliveryapi.domain.cart.application;

import com.github.mingyu.fooddeliveryapi.domain.cart.domain.dto.CartItemResponseDto;
import com.github.mingyu.fooddeliveryapi.domain.cart.domain.CartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem toEntity(CartItem dto);
    List<CartItemResponseDto> toDtos(List<CartItem> entity);
}
