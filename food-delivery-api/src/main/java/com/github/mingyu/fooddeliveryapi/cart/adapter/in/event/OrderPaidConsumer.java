package com.github.mingyu.fooddeliveryapi.cart.adapter.in.event;

import com.github.mingyu.fooddeliveryapi.cart.application.service.CartService;
import com.github.mingyu.fooddeliveryapi.cart.domain.Cart;
import com.github.mingyu.fooddeliveryapi.cart.adapter.out.persistence.CartRepository;
import com.github.mingyu.fooddeliveryapi.cart.domain.CartStatus;
import com.github.mingyu.fooddeliveryapi.domain.order.domain.Order;
import com.github.mingyu.fooddeliveryapi.domain.order.domain.OrderStatus;
import com.github.mingyu.fooddeliveryapi.domain.order.event.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaidConsumer {

    private final CartRepository cartRepository;
    private final CartService cartService;

    /*
    * 주문 결제시 장바구니 캐시 및 DB 동기화 처리
    * */

    @KafkaListener(topics = "order-paid", groupId = "cart-sync-instant", containerFactory = "orderPaidKafkaListenerContainerFactory")
    public void cartSyncInstant(OrderPaidEvent event){
        Order order = event.getOrder();
        // 즉시 동기화
        if (OrderStatus.PAID.equals(order.getStatus())) {
            if(order != null && order.getUserId() != null){
                throw new IllegalArgumentException("필수 값이 존재하지않습니다.");
            }
            List<Cart> carts = cartRepository.findByUserIdAndStatus(order.getUserId(), CartStatus.ACTIVE);
            if(carts != null && carts.size() > 0){
                throw new RuntimeException("장바구니가 존재하지않습니다.");
            }
            Cart cart = carts.get(0);
            cart.changeStatus(CartStatus.PAID);
            cartRepository.save(cart);
            cartService.clearCart(cart.getUserId());
        }
    }
}
