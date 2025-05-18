package com.github.mingyu.fooddeliveryapi.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.domain.CartItem;
import com.github.mingyu.fooddeliveryapi.dto.cart.CartResponseDto;
import com.github.mingyu.fooddeliveryapi.dto.cart.CartUpdateDto;
import com.github.mingyu.fooddeliveryapi.dto.cart.ItemAddRequestDto;
import com.github.mingyu.fooddeliveryapi.entity.Cart;
import com.github.mingyu.fooddeliveryapi.enums.OrderStatus;
import com.github.mingyu.fooddeliveryapi.event.dto.CartEvent;
import com.github.mingyu.fooddeliveryapi.event.producer.CartEventProducer;
import com.github.mingyu.fooddeliveryapi.mapper.CartMapper;
import com.github.mingyu.fooddeliveryapi.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final CartMapper cartMapper;
    private final CartEventProducer cartEventProducer;
    private final CartRepository cartRepository;

    public void addToCartItem(ItemAddRequestDto request) {
        String key = "cart:" + request.getUserId();
        String cartJson = redisTemplate.opsForValue().get(key);

        try {
            Cart cart;
            if (cartJson != null) {
                cart = objectMapper.readValue(cartJson, Cart.class);

                // 가게가 다르면 장바구니 초기화
                if (!Objects.equals(cart.getStoreId(), request.getStoreId())) {
                    cart = new Cart();
                    cart.setUserId(request.getUserId());
                    cart.setStoreId(request.getStoreId());
                }
            } else {
                cart = new Cart();
                cart.setUserId(request.getUserId());
                cart.setStoreId(request.getStoreId());
            }

            // 기존 아이템과 중복 확인
            List<CartItem> cartItems = deserializCartItems(cart);

            // 동일한 menuId와 menuOptionIds를 가진 CartItem 찾기
            Optional<CartItem> existing = cartItems.stream()
                    .filter(item -> Objects.equals(item.getMenuId(), request.getMenuId()))
                    .filter(item -> Objects.equals(
                            item.getMenuOptionIds() != null ? new HashSet<>(item.getMenuOptionIds()) : null,
                            request.getMenuOptionIds() != null ? new HashSet<>(request.getMenuOptionIds()) : null))
                    .findFirst();

            if (existing.isPresent()) {
                // 수량 증가
                CartItem item = existing.get();
                item.setQuantity(item.getQuantity() + request.getQuantity());
            } else {
                // 새로운 아이템 생성
                CartItem item = new CartItem();
                item.setMenuId(request.getMenuId());
                item.setMenuOptionIds(request.getMenuOptionIds());
                item.setQuantity(request.getQuantity());
                item.setPrice(request.getPrice());

                cartItems.add(item);
            }

            String cartItemsJson = objectMapper.writeValueAsString(cartItems);
            cart.setItems(cartItemsJson);

            //캐시 업데이트
            String updatedJson = objectMapper.writeValueAsString(cart);
            redisTemplate.opsForValue().set(key, updatedJson);
            redisTemplate.opsForValue().setIfAbsent("cart:synced:user:" + request.getUserId(), "false");

            // Kafka 이벤트 발행
            CartEvent event = new CartEvent();
            event.setUserId(request.getUserId().toString());
            event.setStatus(OrderStatus.BEFORE_CREATE);
            event.setItemId(request.getMenuId().toString());
            event.setTimestamp(Instant.now().toString());
            event.setStoreId(request.getStoreId());

            cartEventProducer.sendCartEvent(event);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("장바구니 저장 실패", e);
        }
    }

    public CartResponseDto getCart(Long userId) {
        String key = "cart:" + userId;
        String cartJson = redisTemplate.opsForValue().get(key);

        if (cartJson == null) {
            List<Cart> cart = cartRepository.findByUserIdAndStatus(userId, OrderStatus.BEFORE_CREATE);
            if(cart.isEmpty()){
                return CartResponseDto.empty(userId);
            }

            try {
                cartJson = objectMapper.writeValueAsString(cart.get(0));
                redisTemplate.opsForValue().set(key, cartJson);
            }catch (JsonProcessingException e){
                throw new RuntimeException("Json 변환 실패", e);
            }
        }

        try {
            Cart cart = objectMapper.readValue(cartJson, Cart.class);
            CartResponseDto dto = cartMapper.toDto(cart);
            return dto;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("장바구니 불러오기 실패", e);
        }
    }

    public void updateQuantity(CartUpdateDto updateDto) {
        Long userId = updateDto.getUserId();
        Long menuId = updateDto.getMenuId();
        int quantity = updateDto.getQuantity();

        String key = "cart:" + userId;
        String cartJson = redisTemplate.opsForValue().get(key);
        if (cartJson == null) return;

        try {

            Cart cart = objectMapper.readValue(cartJson, Cart.class);

            List<CartItem> cartItems = deserializCartItems(cart);

            // menuId에 해당하는 CartItem 찾아 수량 업데이트
            Optional<CartItem> updated = cartItems.stream()
                    .filter(item -> Objects.equals(item.getMenuId(), menuId))
                    .findFirst();


            updated.get().setQuantity(quantity);

            String cartItemsJson = objectMapper.writeValueAsString(cartItems);
            cart.setItems(cartItemsJson);

            //캐시 업데이트
            String updatedJson = objectMapper.writeValueAsString(cart);
            redisTemplate.opsForValue().set(key, updatedJson);
            redisTemplate.opsForValue().setIfAbsent("cart:synced:user:" + userId, "false");

            CartEvent event = new CartEvent();
            event.setUserId(userId.toString());
            event.setStatus(OrderStatus.BEFORE_CREATE);
            event.setItemId(menuId.toString());
            event.setTimestamp(Instant.now().toString());
            event.setStoreId(cart.getStoreId());

            cartEventProducer.sendCartEvent(event);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("장바구니 수량 수정 실패", e);
        }
    }

    public void removeItem(CartUpdateDto updateDto) {
        Long userId = updateDto.getUserId();
        Long menuId = updateDto.getMenuId();

        String key = "cart:" + userId;
        String cartJson = redisTemplate.opsForValue().get(key);
        if (cartJson == null) return;

        try {
            Cart cart = objectMapper.readValue(cartJson, Cart.class);

            List<CartItem> cartItems = deserializCartItems(cart);
            cartItems.removeIf(item -> item.getMenuId().equals(menuId));

            String cartItemJson = objectMapper.writeValueAsString(cartItems);
            cart.setItems(cartItemJson);

            //캐시 업데이트
            String updatedJson = objectMapper.writeValueAsString(cart);
            redisTemplate.opsForValue().set(key, updatedJson);
            redisTemplate.opsForValue().setIfAbsent("cart:synced:user:" + userId, "false");


            CartEvent event = new CartEvent();
            event.setUserId(userId.toString());
            event.setStatus(OrderStatus.BEFORE_CREATE);
            event.setItemId(menuId.toString());
            event.setTimestamp(Instant.now().toString());

            event.setStoreId(cart.getStoreId());

            cartEventProducer.sendCartEvent(event);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("장바구니 항목 삭제 실패", e);
        }
    }

    public void clearCart(Long userId) {
        String key = "cart:" + userId;
        redisTemplate.delete(key);
        redisTemplate.delete("cart:synced:user:" + userId);
    }


    public List<CartItem> deserializCartItems(Cart cart) throws JsonProcessingException {
        /* Cart.items를 List<CartItem>으로 역직렬화  */
        try {
            List<CartItem> cartItems = cart.getItems() != null && !cart.getItems().isEmpty() ?
                    objectMapper.readValue(cart.getItems(), new TypeReference<List<CartItem>>() {}) : new ArrayList<>();
            return cartItems;
        }catch (JsonProcessingException e) {
            throw new RuntimeException("Cart.items 역직렬화 실패", e);
        }
    }
}
