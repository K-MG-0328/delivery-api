package com.github.mingyu.fooddeliveryapi.domain.order.application;

import com.github.mingyu.fooddeliveryapi.domain.order.domain.Order;
import com.github.mingyu.fooddeliveryapi.domain.order.presentation.dto.OrderCreateResponse;
import com.github.mingyu.fooddeliveryapi.domain.order.presentation.dto.OrderDetailResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderCreateResponse dto);
    Order toOrder(OrderDetailResponse dto);
    OrderCreateResponse toCreateResponseDto(Order order);
    OrderDetailResponse toDetailResponseDto(Order order);
}
