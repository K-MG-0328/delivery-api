package com.github.mingyu.fooddeliveryapi.order.application.service;

import com.github.mingyu.fooddeliveryapi.order.application.port.in.command.OrderParam;
import com.github.mingyu.fooddeliveryapi.order.domain.Order;
import com.github.mingyu.fooddeliveryapi.order.adapter.in.web.request.OrderCreateRequest;
import com.github.mingyu.fooddeliveryapi.order.adapter.in.web.response.OrderDetailResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderParam toOrderParam(Order order);
    OrderParam toOrderParam(OrderCreateRequest request);

    OrderDetailResponse toOrderDetailResponse(OrderParam param);
    List<OrderDetailResponse> toOrderDetailResponses(List<OrderParam> params);


}
