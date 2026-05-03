package com.github.mingyu.fooddeliveryapi.order.application.service;

import com.github.mingyu.fooddeliveryapi.order.application.port.in.command.OrderParam;
import com.github.mingyu.fooddeliveryapi.order.domain.Order;
import com.github.mingyu.fooddeliveryapi.order.domain.OrderFactory;
import com.github.mingyu.fooddeliveryapi.order.application.port.out.OrderRepositoryPort;
import com.github.mingyu.fooddeliveryapi.order.domain.OrderStatus;
import com.github.mingyu.fooddeliveryapi.order.domain.event.OrderPaidEvent;
import com.github.mingyu.fooddeliveryapi.order.adapter.out.event.OrderEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepositoryPort orderRepository;
    private final OrderMapper orderMapper;
    private final OrderEventProducer orderEventProducer;

    @Transactional
    public void createOrder(OrderParam param) {
        Order order = OrderFactory.createOrder(param);

        /* 결제 처리*/
        order.addPaymentMethod(param.getPaymentMethod());
        order.addRequests(param.getRequests());
        order.changeStatus(OrderStatus.PAID);
        orderRepository.save(order);

        // 결제 이후 이벤트 발행
        OrderPaidEvent event = new OrderPaidEvent(order, Instant.now().toString());
        orderEventProducer.sendOrderPaidEvent(event);
    }

    @Transactional(readOnly = true)
    public List<OrderParam> getUserOrders(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderParam> orderList = orders.stream()
                .map(orderMapper::toOrderParam)
                .collect(Collectors.toList());

        return orderList;
    }

    @Transactional(readOnly = true)
    public OrderParam getOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문을 찾을 수 없습니다."));

        OrderParam orderParam = orderMapper.toOrderParam(order);
        return orderParam;
    }

    @Transactional
    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문을 찾을 수 없습니다."));

        order.changeStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
