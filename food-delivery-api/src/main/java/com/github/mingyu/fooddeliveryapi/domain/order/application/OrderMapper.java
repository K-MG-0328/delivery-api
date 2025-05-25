package com.github.mingyu.fooddeliveryapi.domain.order.application;

import com.github.mingyu.fooddeliveryapi.domain.order.domain.Order;
import com.github.mingyu.fooddeliveryapi.domain.order.domain.dto.OrderCreateResponseDto;
import com.github.mingyu.fooddeliveryapi.domain.order.domain.dto.OrderDetailResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderCreateResponseDto dto);
    Order toOrder(OrderDetailResponseDto dto);
    OrderCreateResponseDto toCreateResponseDto(Order order);
    OrderDetailResponseDto toDetailResponseDto(Order order);
}
