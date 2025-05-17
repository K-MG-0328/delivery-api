package com.github.mingyu.fooddeliveryapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.domain.CartSync;
import com.github.mingyu.fooddeliveryapi.entity.Cart;
import com.github.mingyu.fooddeliveryapi.domain.CartItem;
import com.github.mingyu.fooddeliveryapi.dto.order.*;
import com.github.mingyu.fooddeliveryapi.dto.store.StoreResponseDto;
import com.github.mingyu.fooddeliveryapi.dto.user.UserResponseDto;
import com.github.mingyu.fooddeliveryapi.entity.*;
import com.github.mingyu.fooddeliveryapi.enums.OrderStatus;
import com.github.mingyu.fooddeliveryapi.event.dto.OrderPaidEvent;
import com.github.mingyu.fooddeliveryapi.event.producer.OrderEventProducer;
import com.github.mingyu.fooddeliveryapi.mapper.OrderMapper;
import com.github.mingyu.fooddeliveryapi.mapper.StoreMapper;
import com.github.mingyu.fooddeliveryapi.mapper.UserMapper;
import com.github.mingyu.fooddeliveryapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OrderItemRepository orderItemRepository;
    private final OrderItemOptionRepository orderItemOptionRepository;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;

    private final CartService cartService;

    private final OrderEventProducer orderEventProducer;

    @Transactional
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto request) {
        String key = "cart:" + request.getUserId();
        String cartSyncJson = redisTemplate.opsForValue().get(key);

        if(cartSyncJson == null){
            OrderCreateResponseDto response = new OrderCreateResponseDto();
            response.setOrderStatus(OrderStatus.FAILED);
            return response;
        }

        //사용자, 가게 정보
        User user = userRepository.findById(request.getUserId()).orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다."));
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemOption> oiOptions = new ArrayList<>();

        try{
            order.setStatus(OrderStatus.CREATED);
            order.setUser(user);
            order.setStore(store);

            //장바구니 정보
            CartSync cartSync = objectMapper.readValue(cartSyncJson, CartSync.class);
            Cart cart = cartSync.getCart();

            List<CartItem> cartItems = cartService.deserializCartItems(cart);

            //메뉴아이디 가져오기
            Set<Long> menuIds   = cartItems.stream()
                    .map(CartItem::getMenuId)
                    .collect(Collectors.toSet());
            //메뉴 옵션 아이디 가져오기
            Set<Long> optionIds = cartItems.stream()
                    .flatMap(ci -> ci.getMenuOptionIds().stream())
                    .collect(Collectors.toSet());

            // 메뉴 Map
            Map<Long, Menu> menusById   = menuRepository.findByMenuIdIn(menuIds)
                    .stream().collect(Collectors.toMap(Menu::getMenuId, m -> m));
            // 메뉴옵션 Map
            Map<Long, MenuOption> optionsById = menuOptionRepository.findByMenuOptionIdIn(optionIds)
                    .stream().collect(Collectors.toMap(MenuOption::getMenuOptionId, o -> o));

            int totalMenuPrice = 0;       //주문 메뉴 총 가격
            int totalOptionPrice = 0;     //주문 메뉴 옵션 총 가격

            for (CartItem ci : cartItems) {
                Menu menu = menusById.get(ci.getMenuId());
                if (menu == null) throw new RuntimeException("메뉴 없음");

                OrderItem oi = new OrderItem();
                oi.setOrder(order);
                oi.setMenu(menu);
                oi.setPrice(ci.getPrice());
                oi.setQuantity(ci.getQuantity());

                totalMenuPrice += ci.getPrice() * ci.getQuantity();

                for (Long optId : ci.getMenuOptionIds()) {
                    MenuOption mo = optionsById.get(optId);
                    if (mo == null) throw new RuntimeException("옵션 없음");

                    OrderItemOption oio = orderMapper.convertFrom(mo);
                    oio.setOrderItem(oi);
                    oiOptions.add(oio);

                    totalOptionPrice += oio.getPrice() * ci.getQuantity();
                }

                orderItems.add(oi);
            }

            order.setTotalPrice(totalMenuPrice + totalOptionPrice);

            order = orderRepository.save(order);
            orderItemRepository.saveAll(orderItems);
            orderItemOptionRepository.saveAll(oiOptions);

            redisTemplate.delete(key);

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

    @Transactional
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

        // 결제 이후 이벤트 발행
        OrderPaidEvent event = new OrderPaidEvent(
                order.getOrderId(),
                order.getUser().getUserId(),
                order.getStore().getStoreId(),
                order.getTotalPrice(),
                order.getCreatedDate().toString(),
                order.getUser().getCurrentAddress()
        );
        orderEventProducer.sendOrderPaidEvent(event);
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

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("해당 주문을 찾을 수 없습니다."));

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
