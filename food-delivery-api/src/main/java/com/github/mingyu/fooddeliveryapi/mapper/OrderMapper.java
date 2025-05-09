package com.github.mingyu.fooddeliveryapi.mapper;

import com.github.mingyu.fooddeliveryapi.dto.order.OrderCreateResponseDto;
import com.github.mingyu.fooddeliveryapi.dto.order.OrderDetailResponseDto;
import com.github.mingyu.fooddeliveryapi.entity.MenuOption;
import com.github.mingyu.fooddeliveryapi.entity.Order;
import com.github.mingyu.fooddeliveryapi.entity.OrderItemOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "option", target = "optionName")
    @Mapping(target = "orderItem", ignore = true) //해당 필드를 매핑에서 제외
    OrderItemOption convertFrom(MenuOption option);

    OrderCreateResponseDto toOrderCreateResponseDto(Order order);

    OrderDetailResponseDto toOrderDetailResponseDto(Order order);

}
