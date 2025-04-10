package com.github.mingyu.fooddeliveryapi.mapper;

import com.github.mingyu.fooddeliveryapi.dto.order.OrderResponseDto;
import com.github.mingyu.fooddeliveryapi.entity.MenuOption;
import com.github.mingyu.fooddeliveryapi.entity.Order;
import com.github.mingyu.fooddeliveryapi.entity.OrderItemOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "option", target = "optionName")
    @Mapping(target = "orderItem", ignore = true)
    OrderItemOption convertFrom(MenuOption option);

    OrderResponseDto toDto(Order order);
}
