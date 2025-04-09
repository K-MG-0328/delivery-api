package com.github.mingyu.fooddeliveryapi.mapper;

import com.github.mingyu.fooddeliveryapi.domain.Cart;
import com.github.mingyu.fooddeliveryapi.dto.cart.CartResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartResponseDto toDto(Cart cart);
}
