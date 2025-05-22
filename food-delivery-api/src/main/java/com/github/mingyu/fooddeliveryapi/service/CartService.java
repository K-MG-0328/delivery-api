package com.github.mingyu.fooddeliveryapi.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.dto.cart.CartItemAddRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.cart.CartItemResponseDto;
import com.github.mingyu.fooddeliveryapi.dto.cart.CartResponseDto;
import com.github.mingyu.fooddeliveryapi.dto.cart.CartUpdateDto;
import com.github.mingyu.fooddeliveryapi.entity.Cart;
import com.github.mingyu.fooddeliveryapi.entity.CartItem;
import com.github.mingyu.fooddeliveryapi.entity.CartItemOption;
import com.github.mingyu.fooddeliveryapi.enums.CartStatus;
import com.github.mingyu.fooddeliveryapi.event.dto.CartEvent;
import com.github.mingyu.fooddeliveryapi.event.producer.CartEventProducer;
import com.github.mingyu.fooddeliveryapi.mapper.CartItemMapper;
import com.github.mingyu.fooddeliveryapi.mapper.CartMapper;
import com.github.mingyu.fooddeliveryapi.repository.CartItemRepository;
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
    private final RedisTemplate<String, Cart> cartRedisTemplate;
    private final RedisTemplate<String, List<CartItem>> cartItemRedisTemplate;
    private final ObjectMapper objectMapper;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartEventProducer cartEventProducer;

    /*
    * 레디스 키 관리
    * cart:{userId} - 장바구니 키  CartItem
    * cart:items:{userId} - 장바구니 아이템 목록 키 List<CartItem>
    * cart:synced:{userId} - 장바구니 캐시 DB 동기화 여부 true/false
    * */

    public void addToCartItem(CartItemAddRequestDto request) {

        String cartKey = "cart:" + request.getUserId();
        String cartItemsKey = "cart:items:" + request.getUserId();

        //캐시 및 db 확인
        Cart cart = cartRedisTemplate.opsForValue().get(cartKey);
        List<CartItem> cartItems = cartItemRedisTemplate.opsForValue().get(cartItemsKey);
        if (cart == null) {
            List<Cart> cartList = cartRepository.findByUserIdAndStatus(request.getUserId(), CartStatus.ACTIVE);
            cartItems = cartItemRepository.findByCartId(cartList.get(0).getCartId());
            cart = cartList.get(0);
        }

        try {
            //장바구니 확인 및 가게가 다를 경우 새로 생성
            if(cart == null || !Objects.equals(cart.getStoreId(), request.getStoreId())){
                /*캐시 초기화*/
                cartRedisTemplate.delete(cartKey);
                cartItemRedisTemplate.delete(cartItemsKey);

                cart = new Cart();
                cart.setUserId(request.getUserId());
                cart.setStoreId(request.getStoreId());
                cart.setStatus(CartStatus.ACTIVE);
                cart.setTotalPrice(0);
                cartItems = new ArrayList<>();
            }


            // 동일한 menuId와 options 가진 CartItem 찾기
            Optional<CartItem> existing = cartItems.stream()
                    .filter(item -> Objects.equals(item.getMenuId(), request.getMenuId()))
                    .filter(item -> {
                        try{
                            //Json -> List<CartItemOption>
                            List<CartItemOption> existingOptions = objectMapper.readValue(
                                    item.getOptions(), new TypeReference<List<CartItemOption>>() {}
                            );

                            //set으로 변환
                            Set<CartItemOption> existingOpts = existingOptions == null ? Collections.emptySet()
                                    : new HashSet<>(existingOptions);

                            Set<CartItemOption> requestOpts = request.getOptions() == null ? Collections.emptySet()
                                    : new HashSet<>(request.getOptions());

                            // 값(동일한 옵션의 존재 여부) 비교
                            return Objects.equals(existingOpts, requestOpts);
                        }catch (JsonProcessingException e){
                            throw new RuntimeException("아이템 비교 실패",e);
                        }
                    })
                    .findFirst();

            if (existing.isPresent()) {
                // 수량 증가
                CartItem item = existing.get();
                item.setQuantity(item.getQuantity() + request.getQuantity());
            } else {
                // 새로운 아이템 생성
                CartItem item = new CartItem();
                item.setMenuId(request.getMenuId());
                item.setName(request.getMenuName());
                item.setPrice(request.getPrice());
                item.setQuantity(request.getQuantity());

                String options = objectMapper.writeValueAsString(request.getOptions());
                item.setOptions(options);

                cartItems.add(item);
            }

            /* 총 가격을 계산 */
            int totalPrice = calculateTotalPrice(cartItems);
            cart.setTotalPrice(totalPrice);

            // Cart, CartItems 캐시
            cartRedisTemplate.opsForValue().set(cartKey, cart);
            cartItemRedisTemplate.opsForValue().set(cartItemsKey, cartItems);

            // DB 동기화 여부
            redisTemplate.opsForValue().setIfAbsent("cart:synced:" + request.getUserId(), "false");

            // DB 동기화 이벤트 발행
            CartEvent event = new CartEvent();
            event.setCart(cart);
            event.setCartItems(cartItems);
            event.setTimestamp(Instant.now().toString());

            cartEventProducer.sendCartEvent(event);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("장바구니 저장 실패", e);
        }
    }

    public CartResponseDto getCart(Long userId) {
        String cartKey = "cart:" + userId;
        String cartItemKey = "cart:items" + userId;

        Cart cart = cartRedisTemplate.opsForValue().get(cartKey);
        List<CartItem> cartItems = cartItemRedisTemplate.opsForValue().get(cartItemKey);

        //cart null이면 db 조회 후 존재하면 다시 캐시
        if (cart == null) {
            List<Cart> cartList = cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE);
            cartItems = cartItemRepository.findByCartId(cartList.get(0).getCartId());
            if(cartList.isEmpty()){
                return CartResponseDto.empty(userId);
            }
            cartRedisTemplate.opsForValue().set(cartKey, cartList.get(0));
            cartItemRedisTemplate.opsForValue().set(cartItemKey, cartItems);
        }

        //장바구니 정보 반환
        CartResponseDto cartResponseDto = cartMapper.toDto(cart);
        List<CartItemResponseDto> cartItemResponseDto = cartItemMapper.toDtos(cartItems);
        cartResponseDto.setItems(cartItemResponseDto);

        return cartResponseDto;
    }

    public void updateQuantity(CartUpdateDto updateDto) {
        Long userId = updateDto.getUserId();
        Long cartItemId = updateDto.getCartItemId();
        String options = updateDto.getOptions();
        int quantity = updateDto.getQuantity();

        String cartKey = "cart:" + userId;
        String cartItemKey = "cart:items" + userId;
        Cart cart = cartRedisTemplate.opsForValue().get(cartKey);
        List<CartItem> cartItems = cartItemRedisTemplate.opsForValue().get(cartItemKey);

        // cartItemId에 해당하는 CartItem 찾아서 옵션 및 수량 업데이트
        Optional<CartItem> updated = cartItems.stream()
                .filter(item -> Objects.equals(item.getMenuId(), cartItemId))
                .findFirst();

        updated.get().setQuantity(quantity);
        updated.get().setOptions(options);

        //장바구니 목록 계산
        int totalPrice = calculateTotalPrice(cartItems);
        cart.setTotalPrice(totalPrice);

        //장바구니, 목록, db 동기화 여부
        cartRedisTemplate.opsForValue().set(cartKey, cart);
        cartItemRedisTemplate.opsForValue().set(cartItemKey, cartItems);
        redisTemplate.opsForValue().setIfAbsent("cart:synced:" + userId, "false");

        // DB 동기화 이벤트 발행
        CartEvent event = new CartEvent();
        event.setCart(cart);
        event.setCartItems(cartItems);
        event.setTimestamp(Instant.now().toString());
        cartEventProducer.sendCartEvent(event);
    }

    public void removeItem(CartUpdateDto updateDto) {

        Long userId = updateDto.getUserId();
        Long cartItemId = updateDto.getCartItemId();

        String cartKey = "cart:" + userId;
        String cartItemsKey = "cart:items:" + userId;

        //장바구니 확인
        Cart cart = cartRedisTemplate.opsForValue().get(cartKey);
        List<CartItem> cartItems = cartItemRedisTemplate.opsForValue().get(cartItemsKey);

        //장바구니 아이템 제거
        Optional<CartItem> existing = cartItems.stream().filter(item -> Objects.equals(item.getCartItemId(), cartItemId)).findFirst();
        if(!existing.isEmpty()){
            cartItems.remove(existing.get());
            cartItemRepository.delete(existing.get());
        }

        //cart가 비워졌으면 즉시 동기화
        if(cartItems.isEmpty()){
            clearCart(userId);
            return;
        }

        //캐시 업데이트
        cartRedisTemplate.opsForValue().set(cartKey, cart);
        cartItemRedisTemplate.opsForValue().set(cartItemsKey, cartItems);

        // DB 동기화 여부
        redisTemplate.opsForValue().setIfAbsent("cart:synced:" + userId, "false");

        // DB 동기화 이벤트 발행
        CartEvent event = new CartEvent();
        event.setCart(cart);
        event.setCartItems(cartItems);
        event.setTimestamp(Instant.now().toString());

        cartEventProducer.sendCartEvent(event);
    }

    public void clearCart(Long userId) {
        String cartKey = "cart:" + userId;
        String cartItemKey = "cart:items" + userId;

        //장바구니 비우면 즉시 동기화
        Cart cart = cartRedisTemplate.opsForValue().get(cartKey);
        cartRepository.save(cart);

        List<CartItem> cartItems = cartItemRedisTemplate.opsForValue().get(cartItemKey);
        cartItemRepository.deleteAll(cartItems);

        //캐시 제거
        cartRedisTemplate.delete(cartKey);
        cartItemRedisTemplate.delete(cartItemKey);
        redisTemplate.delete("cart:synced:" + userId);
    }

    public int calculateTotalPrice(List<CartItem> cartItems) {
        int totalPrice = cartItems.stream().mapToInt(item -> {
            try{
                List<CartItemOption> options = objectMapper.readValue(item.getOptions(), new TypeReference<List<CartItemOption>>() {});
                int optionTotalPrice = options.stream().mapToInt(option -> option.getPrice()).sum();
                return (optionTotalPrice + item.getPrice()) * item.getQuantity();
            }catch (JsonProcessingException e){
                throw new RuntimeException("");
            }
        }).sum();

        return totalPrice;
    }
}
