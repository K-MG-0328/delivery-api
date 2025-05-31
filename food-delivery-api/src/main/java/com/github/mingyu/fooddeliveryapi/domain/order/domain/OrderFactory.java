package com.github.mingyu.fooddeliveryapi.domain.order.domain;

import com.github.mingyu.fooddeliveryapi.common.util.IdGenerator;
import com.github.mingyu.fooddeliveryapi.domain.order.application.dto.OrderCommand;
import com.github.mingyu.fooddeliveryapi.domain.order.application.dto.OrderItemOptionParam;
import com.github.mingyu.fooddeliveryapi.domain.order.application.dto.OrderItemParam;
import com.github.mingyu.fooddeliveryapi.domain.order.application.dto.OrderParam;

import java.util.ArrayList;
import java.util.List;

public class OrderFactory {

    private OrderFactory() {}

    public static Order createOrder(OrderCommand command) {

        Order order = new Order();

        if (command instanceof OrderParam param) {
            StoreInfo storeInfo = new StoreInfo(param.getStoreId(), param.getStoreName(), param.getStorePhone(), param.getStoreAddress());
            order = createOrder(param.getUserId(), storeInfo, param.getItems());
        }

        return order;
    }

    public static Order createOrder(Long userId, StoreInfo storeInfo, List<OrderItemParam> items) {

        String orderId = IdGenerator.uuid();
        Order order = new Order(orderId, userId, storeInfo, OrderStatus.CREATED);

        if(items.isEmpty()){
           throw new RuntimeException("주문 목록이 존재하지않습니다.");
        }

        List<OrderItem> orderItems = createOrderItems(items);
        for (OrderItem orderItem : orderItems) {
            order.addItem(orderItem);
        }

        return order;
    }

    public static List<OrderItem> createOrderItems(List<OrderItemParam> items) {
        String itemId = IdGenerator.uuid();
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemParam param : items) {
            OrderItem orderItem = new OrderItem(itemId, param.getMenuId(), param.getName(), param.getPrice(), param.getQuantity());
            List<OrderItemOption> orderItemOptions = createOrderItemOptions(itemId, param.getOptions());
            orderItem.addOptions(orderItemOptions);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    public static List<OrderItemOption> createOrderItemOptions(String itemId, List<OrderItemOptionParam> options) {
        List<OrderItemOption> orderItemOptions = new ArrayList<>();
        for (OrderItemOptionParam param : options) {
            OrderItemOption orderItemOption = new OrderItemOption(itemId, param.getOptionName(), param.getPrice());
            orderItemOptions.add(orderItemOption);
        }
        return orderItemOptions;
    }
}
