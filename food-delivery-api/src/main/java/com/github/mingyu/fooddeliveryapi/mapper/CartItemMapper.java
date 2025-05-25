package com.github.mingyu.fooddeliveryapi.mapper;

import com.github.mingyu.fooddeliveryapi.dto.cart.CartItemResponseDto;
import com.github.mingyu.fooddeliveryapi.entity.CartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem toEntity(CartItem dto);
    List<CartItemResponseDto> toDtos(List<CartItem> entity);
}
