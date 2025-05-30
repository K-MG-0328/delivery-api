package com.github.mingyu.fooddeliveryapi.domain.cart.application;


import com.github.mingyu.fooddeliveryapi.domain.cart.application.dto.SingleCartParam;
import com.github.mingyu.fooddeliveryapi.domain.cart.application.dto.CartParam;
import com.github.mingyu.fooddeliveryapi.domain.cart.domain.*;
import com.github.mingyu.fooddeliveryapi.domain.cart.event.CartEvent;
import com.github.mingyu.fooddeliveryapi.domain.cart.infrastructure.producer.CartEventProducer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;


@Service
@RequiredArgsConstructor
public class CartService {

    @PersistenceContext
    private EntityManager em;

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, Cart> cartRedisTemplate;
    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final CartEventProducer cartEventProducer;

    /*
    * 레디스 키 관리
    * cart:{userId} - 장바구니 키  Item
    * cart:synced:{userId} - 장바구니 캐시 DB 동기화 여부 true/false
    * */

    @Transactional
    public void addToCartItem(SingleCartParam param) {
        String cartKey = "cart:" + param.getCartId();
        Cart cart = searchCart(param.getUserId(), cartKey);

        //장바구니 확인 및 가게가 다를 경우 새로 생성
        if(cart == null || !Objects.equals(cart.getStoreId(), param.getStoreId())){
            /*캐시 초기화*/
            cartRedisTemplate.delete(cartKey);
            cartRepository.deleteCart(cart); //벌크 삭제
            em.detach(cart); // 영속성 컨텍스트 초기화
            cart = CartFactory.createCart(param);
        }

        // 동일한 menuId와 options 가진 Item 찾기
        Optional<CartItem> existing = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getItemId(), param.getItem()))
                .filter(item -> {
                        List<CartItemOption> existingOptions = item.getOptions();
                        //set으로 변환
                        Set<CartItemOption> existingOpts = existingOptions == null ? Collections.emptySet()
                                : new HashSet<>(existingOptions);

                        List<CartItemOption> compareOpts = CartFactory.createCartItemOptions(param.getItem().getItemId(), param.getItem().getOptions());
                        Set<CartItemOption> generateOpts = compareOpts == null ? Collections.emptySet()
                                : new HashSet<>(compareOpts);

                        // 값(동일한 옵션의 존재 여부) 비교
                        return Objects.equals(existingOpts, generateOpts);
                })
                .findFirst();
        if (existing.isPresent()) {
            // 수량 증가
            CartItem item = existing.get();
            item.changeQuantity(item.getQuantity() + param.getItem().getQuantity());
        } else {
            // 새로운 아이템 생성
            CartItem item = CartFactory.createCartItem(param.getItem());
            cart.addItem(item);
        }

        // Cart, DB 동기화 여부 캐시
        cartRedisTemplate.opsForValue().set(cartKey, cart);
        redisTemplate.opsForValue().setIfAbsent("cart:synced:" + param.getUserId(), "false");

        // DB 동기화 이벤트 발행
        CartEvent event = new CartEvent(cart, Instant.now().toString());
        cartEventProducer.sendCartEvent(event);
    }

    @Transactional(readOnly = true)
    public CartParam getCart(Long userId) {
        String cartKey = "cart:" + userId;

        //cart null이면 db 조회 후 존재하면 다시 캐시
        Cart cart = cartRedisTemplate.opsForValue().get(cartKey);
        if (cart == null) {
            List<Cart> cartList = cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE);
            if(cartList.isEmpty()){
                throw new IllegalStateException("카트가 비어있습니다.");
            }
            cartRedisTemplate.opsForValue().set(cartKey, cartList.get(0));
        }

        CartParam response = cartMapper.toCartParam(cart);
        return response;
    }

    @Transactional
    public void updateItem(SingleCartParam param) {
        Long userId = param.getUserId();
        String ItemId = param.getItem().getItemId();
        int quantity = param.getItem().getQuantity();

        String cartKey = "cart:" + userId;
        Cart cart = cartRedisTemplate.opsForValue().get(cartKey);

        // cartItemId에 해당하는 Item 찾아서 옵션 및 수량 업데이트
        Optional<CartItem> updateItem = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getItemId(), ItemId))
                .findFirst();

        List<CartItemOption> requestOptions = CartFactory.createCartItemOptions(param.getItem().getItemId(), param.getItem().getOptions());

        updateItem.get().changeQuantity(quantity);
        updateItem.get().changeOptions(requestOptions);  //Embeddable 타입이라 쿼리 1번 호출


        //장바구니, 목록, db 동기화 여부
        cartRedisTemplate.opsForValue().set(cartKey, cart);
        redisTemplate.opsForValue().setIfAbsent("cart:synced:" + userId, "false");

        // DB 동기화 이벤트 발행
        CartEvent event = new CartEvent(cart, Instant.now().toString());
        cartEventProducer.sendCartEvent(event);
    }

    @Transactional
    public void removeItem(SingleCartParam param) {
        Long userId = param.getUserId();
        String cartItemId = param.getItem().getItemId();
        String cartKey = "cart:" + userId;

        Cart cart = searchCart(userId, cartKey);

        //장바구니 아이템 제거
        Optional<CartItem> existing = cart.getItems().stream().filter(item -> Objects.equals(item.getItemId(), cartItemId)).findFirst();
        if (existing.isPresent()) {
            cart.removeItem(existing.get());
        }

        //cart가 비워졌으면 즉시 동기화
        if(cart.getItems().isEmpty()){
            clearCart(userId);
            return;
        }

        //캐시 업데이트
        cartRedisTemplate.opsForValue().set(cartKey, cart);
        redisTemplate.opsForValue().setIfAbsent("cart:synced:" + userId, "false");

        // DB 동기화 이벤트 발행
        CartEvent event = new CartEvent(cart, Instant.now().toString());
        cartEventProducer.sendCartEvent(event);
    }

    @Transactional
    public void clearCart(Long userId) {
        String cartKey = "cart:" + userId;

        //장바구니 비우면 즉시 동기화
        Cart cart = cartRedisTemplate.opsForValue().get(cartKey);
        cartRepository.deleteCart(cart);

        //캐시 제거
        cartRedisTemplate.delete(cartKey);
        redisTemplate.delete("cart:synced:" + userId);
    }


    private Cart searchCart(Long userId, String cartKey) {
        //캐시 및 db 확인
        Cart cart = cartRedisTemplate.opsForValue().get(cartKey);
        if (cart == null) {
            List<Cart> cartList = cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE);
            cart = cartList.get(0);
        }
        return cart;
    }
}
