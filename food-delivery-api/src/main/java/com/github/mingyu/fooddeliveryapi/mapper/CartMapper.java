package com.github.mingyu.fooddeliveryapi.mapper;

import com.github.mingyu.fooddeliveryapi.entity.Cart;
import com.github.mingyu.fooddeliveryapi.dto.cart.CartResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    CartResponseDto toDto(Cart cart);
}
