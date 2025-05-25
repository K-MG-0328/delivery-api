package com.github.mingyu.fooddeliveryapi.domain.cart.application;

import com.github.mingyu.fooddeliveryapi.domain.cart.presentation.dto.CartResponse;
import com.github.mingyu.fooddeliveryapi.domain.cart.domain.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    CartResponse toDto(Cart cart);
}
