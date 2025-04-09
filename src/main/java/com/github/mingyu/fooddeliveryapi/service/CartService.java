package com.github.mingyu.fooddeliveryapi.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.domain.Cart;
import com.github.mingyu.fooddeliveryapi.domain.CartItem;
import com.github.mingyu.fooddeliveryapi.dto.cart.CartResponseDto;
import com.github.mingyu.fooddeliveryapi.dto.cart.ItemAddRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.cart.CartUpdateDto;
import com.github.mingyu.fooddeliveryapi.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final CartMapper cartMapper;

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
                    cart.setStoreId(request.getStoreId());
                }
            } else {
                cart = new Cart();
                cart.setStoreId(request.getStoreId());
            }

            // 기존 아이템과 중복 확인
            Optional<CartItem> existing = cart.getItems().stream()
                    .filter(i -> i.getMenuId().equals(request.getMenuId()) &&
                            i.getMenuOptionIds().containsAll(request.getMenuOptionIds()) &&
                            request.getMenuOptionIds().containsAll(i.getMenuOptionIds()))
                    .findFirst();

            if (existing.isPresent()) {
                // 수량 증가
                existing.get().setQuantity(existing.get().getQuantity() + request.getQuantity());
            } else {
                // 새로운 아이템 생성
                CartItem item = new CartItem();
                item.setMenuId(request.getMenuId());
                item.setMenuOptionIds(request.getMenuOptionIds());
                item.setQuantity(request.getQuantity());
                item.setPrice(request.getPrice());

                cart.getItems().add(item);
            }

            String updatedJson = objectMapper.writeValueAsString(cart);
            redisTemplate.opsForValue().set(key, updatedJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("장바구니 저장 실패", e);
        }
    }

    public CartResponseDto getCart(Long userId) {
        String key = "cart:" + userId;
        String cartJson = redisTemplate.opsForValue().get(key);

        if (cartJson == null) {
            return new CartResponseDto();
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
            cart.getItems().stream()
                    .filter(item -> item.getMenuId().equals(menuId))
                    .findFirst()
                    .ifPresent(item -> item.setQuantity(quantity));
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(cart));
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
            cart.getItems().removeIf(item -> item.getMenuId().equals(menuId));
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(cart));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("장바구니 항목 삭제 실패", e);
        }
    }

    public void clearCart(Long userId) {
        String key = "cart:" + userId;
        redisTemplate.delete(key);
    }
}
