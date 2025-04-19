package com.github.mingyu.fooddeliveryapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.domain.Cart;
import com.github.mingyu.fooddeliveryapi.domain.CartItem;
import com.github.mingyu.fooddeliveryapi.dto.order.*;
import com.github.mingyu.fooddeliveryapi.dto.store.StoreResponseDto;
import com.github.mingyu.fooddeliveryapi.dto.user.UserResponseDto;
import com.github.mingyu.fooddeliveryapi.entity.*;
import com.github.mingyu.fooddeliveryapi.enums.OrderStatus;
import com.github.mingyu.fooddeliveryapi.mapper.OrderMapper;
import com.github.mingyu.fooddeliveryapi.mapper.StoreMapper;
import com.github.mingyu.fooddeliveryapi.mapper.UserMapper;
import com.github.mingyu.fooddeliveryapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;

    public OrderCreateResponseDto createOrder(OrderCreateRequestDto request) {
        String key = "cart:" + request.getUserId();
        String cartJson = redisTemplate.opsForValue().get(key);

        if(cartJson == null){
            return new OrderCreateResponseDto();
        }

        //사용자, 가게 정보
        User user = userRepository.findById(request.getUserId()).orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다."));
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        Order order = new Order();
        try{
            order.setStatus(OrderStatus.CREATED);
            order.setUser(user);
            order.setStore(store);

            //장바구니 정보
            Cart cart = objectMapper.readValue(cartJson, Cart.class);
            List<CartItem> cartItems = cart.getItems();     // 장바구니
            List<OrderItem> orderItems = new ArrayList<>(); // 주문목록

            int menuOrderPriceSum = 0;       //주문 메뉴 총 가격
            int menuOrderOptionPriceSum = 0; //주문 메뉴 옵션 총 가격

            for(CartItem cartItem : cartItems){
                //메뉴 정보
                Menu menu = menuRepository.getById(cartItem.getMenuId());

                //주문 정보
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setMenu(menu);
                orderItem.setQuantity(cartItem.getQuantity());

                menuOrderPriceSum += cartItem.getPrice();
                orderItem.setPrice(cartItem.getPrice());

                //메뉴 옵션 정보
                List<Long> menuOptions = cartItem.getMenuOptionIds();
                List<OrderItemOption> orderOptions = new ArrayList<>();
                for(Long optionId : menuOptions){

                    MenuOption menuOption = menuOptionRepository.findById(optionId).orElseThrow(() -> new RuntimeException("존재하지 않는 메뉴 옵션입니다."));

                    OrderItemOption orderOption =  orderMapper.convertFrom(menuOption);
                    orderOption.setOrderItem(orderItem);
                    orderOptions.add(orderOption);

                    menuOrderOptionPriceSum += orderOption.getPrice();
                }

                orderItem.setItemOptions(orderOptions);  //옵션 목록 저장
                orderItems.add(orderItem); //주문 목록 저장
            }

            order.setTotalPrice(menuOrderPriceSum + menuOrderOptionPriceSum);
            order.setItems(orderItems);

            orderRepository.save(order);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("주문 저장 실패", e);
        }

        OrderCreateResponseDto orderResponse = orderMapper.toOrderCreateResponseDto(order);
        UserResponseDto userDto = userMapper.toDto(user);
        StoreResponseDto storeDto = storeMapper.toDto(store);

        orderResponse.setUser(userDto);
        orderResponse.setStore(storeDto);

        return orderResponse;
    }

    public void payOrder(OrderPaymentRequestDto request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("이미 결제되었거나 유효하지 않은 주문입니다.");
        }

        /* 결제 API 호출 (미구현)*/

        order.setRequests(request.getRequests());
        order.setStatus(OrderStatus.PAID);
        order.setPaymentMethod(request.getPaymentMethod());
        orderRepository.save(order);
    }

    public OrderListResponseDto getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUser_UserId(userId);
        List<OrderDetailResponseDto> orderList = orders.stream()
                .map(orderMapper::toOrderDetailResponseDto)
                .collect(Collectors.toList());

        return new OrderListResponseDto(orderList);
    }

    public OrderDetailResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문을 찾을 수 없습니다."));

        return orderMapper.toOrderDetailResponseDto(order);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문을 찾을 수 없습니다."));

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
