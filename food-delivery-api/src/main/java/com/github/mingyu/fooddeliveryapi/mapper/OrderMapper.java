package com.github.mingyu.fooddeliveryapi.mapper;

import com.github.mingyu.fooddeliveryapi.dto.order.OrderCreateResponseDto;
import com.github.mingyu.fooddeliveryapi.dto.order.OrderDetailResponseDto;
import com.github.mingyu.fooddeliveryapi.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderCreateResponseDto dto);
    Order toOrder(OrderDetailResponseDto dto);
    OrderCreateResponseDto toCreateResponseDto(Order order);
    OrderDetailResponseDto toDetailResponseDto(Order order);
}
