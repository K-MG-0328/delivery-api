package com.github.mingyu.fooddeliveryapi.domain.order.application;

import com.github.mingyu.fooddeliveryapi.domain.order.application.dto.OrderParam;
import com.github.mingyu.fooddeliveryapi.domain.order.domain.Order;
import com.github.mingyu.fooddeliveryapi.domain.order.presentation.dto.OrderCreateRequest;
import com.github.mingyu.fooddeliveryapi.domain.order.presentation.dto.OrderDetailResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderParam toOrderParam(Order order);
    OrderParam toOrderParam(OrderCreateRequest request);

    OrderDetailResponse toOrderDetailResponse(OrderParam param);
    List<OrderDetailResponse> toOrderDetailResponses(List<OrderParam> params);


}
