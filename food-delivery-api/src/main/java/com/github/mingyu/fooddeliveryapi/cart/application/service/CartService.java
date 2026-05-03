package com.github.mingyu.fooddeliveryapi.cart.application.service;


import com.github.mingyu.fooddeliveryapi.cart.adapter.in.web.response.CartResponse;
import com.github.mingyu.fooddeliveryapi.cart.adapter.out.event.CartEventProducer;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.AddItemUseCase;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.RemoveItemUseCase;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.SearchCartUseCase;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.UpdateItemUseCase;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.AddItemCommand;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.RemoveItemCommand;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.UpdateItemOptionCommand;
import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.UpdateQuantityCommand;
import com.github.mingyu.fooddeliveryapi.cart.application.port.out.CartRepositoryPort;
import com.github.mingyu.fooddeliveryapi.cart.domain.*;
import com.github.mingyu.fooddeliveryapi.cart.domain.vo.Item;
import com.github.mingyu.fooddeliveryapi.cart.domain.vo.ItemOption;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class CartService implements AddItemUseCase, RemoveItemUseCase, SearchCartUseCase, UpdateItemUseCase {

    @PersistenceContext
    private EntityManager em;

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, Cart> cartRedisTemplate;
    private final CartRepositoryPort cartRepositoryPort;
    private final CartEventProducer cartEventProducer;

    /*
    * 레디스 키 관리
    * cart:{userId} - 장바구니 키  Item
    * cart:synced:{userId} - 장바구니 캐시 DB 동기화 여부 true/false
    * */

    @Override
    @Transactional
    public void addToCartItem(AddItemCommand command) {
        String cartKey = "cart:" + command.getUserId();
        Cart cart = getCart(cartKey);

        //장바구니 확인 및 가게가 다를 경우 새로 생성
        if(cart == null || !Objects.equals(cart.getStoreId(), command.getStoreId())){
            if(cart != null) {
                cartRedisTemplate.delete(cartKey);
                cartRepositoryPort.deleteCart(cart); //벌크 삭제
            }
            /* 쿼리 DSL로 update/delete를 할 경우 영속성 컨텍스트를 무시하고 실행되므로
             해당 엔티티가 여전히 영속 상태일 수 있어 수동 정리 필요 */
            em.clear();
            cart = CartFactory.createCart(command);
        }

        // 동일한 menuId와 options 가진 Item 찾기
        Optional<CartItem> existing = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getItemId(), command.getItem()))
                .filter(item -> {
                        List<CartItemOption> existingOptions = item.getOptions();
                        //set으로 변환
                        Set<CartItemOption> existingOpts = existingOptions == null ? Collections.emptySet()
                                : new HashSet<>(existingOptions);

                        List<CartItemOption> compareOpts = CartFactory.createCartItemOptions(command.getItem().getItemId(), command.getItem().getOptions());
                        Set<CartItemOption> generateOpts = compareOpts == null ? Collections.emptySet()
                                : new HashSet<>(compareOpts);

                        // 값(동일한 옵션의 존재 여부) 비교
                        return Objects.equals(existingOpts, generateOpts);
                })
                .findFirst();
        if (existing.isPresent()) {
            // 수량 증가
            CartItem item = existing.get();
            item.changeQuantity(item.getQuantity() + command.getItem().getQuantity());
        } else {
            // 새로운 아이템 생성
            CartItem item = CartFactory.createCartItem(command.getItem());
            cart.addItem(item);
        }

        CartValidator.validateCart(cart);

        // Cart, DB 동기화 여부 캐시
        cartRedisTemplate.opsForValue().set(cartKey, cart);
        redisTemplate.opsForValue().set("cart:synced:" + command.getUserId(), "false");

        // DB 동기화 이벤트 발행
        CartEvent event = new CartEvent(cart, Instant.now().toString());
        cartEventProducer.sendCartEvent(event);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse searchCart(String userId) {
        String cartKey = "cart:" + userId;
        Cart cart = getCart(cartKey);

        List<Item> items = cart.getItems().stream()
                .map(i -> {
                    List<ItemOption> options = i.getOptions().stream()
                            .map(o -> new ItemOption(o.getItemId(), o.getOptionName(), o.getPrice()))
                            .collect(toList());

                    return new Item(
                            i.getItemId(),
                            i.getMenuId(),
                            i.getName(),
                            i.getPrice(),
                            options,
                            i.getQuantity()
                    );
                })
                .collect(toList());

        return new CartResponse(cart.getCartId(),
                cart.getUserId(),
                cart.getStoreId(),
                cart.getStatus(),
                items,
                cart.getTotalPrice());
    }

    @Override
    @Transactional
    public void updateItemQuantity(UpdateQuantityCommand command) {
        String userId = command.getUserId();
        String itemId = command.getItemId();
        int quantity = command.getQuantity();

        String cartKey = "cart:" + userId;
        Cart cart = getCart(cartKey);

        // cartItemId에 해당하는 Item 찾아서 수량 업데이트
        Optional<CartItem> updateItem = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getItemId(), itemId))
                .findFirst();

        updateItem.get().changeQuantity(quantity);

        CartValidator.validateCart(cart);

        //장바구니, 목록, db 동기화 여부
        cartRedisTemplate.opsForValue().set(cartKey, cart);
        redisTemplate.opsForValue().set("cart:synced:" + userId, "false");

        // DB 동기화 이벤트 발행
        CartEvent event = new CartEvent(cart, Instant.now().toString());
        cartEventProducer.sendCartEvent(event);
    }

    @Override
    @Transactional
    public void updateItemOption(UpdateItemOptionCommand command) {
        String userId = command.getUserId();

        String cartKey = "cart:" + userId;
        Cart cart = getCart(cartKey);

        // cartItemId에 해당하는 Item 찾아서 옵션 및 수량 업데이트
        Optional<CartItem> updateItem = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getItemId(), command.getItem().getItemId()))
                .findFirst();

        List<CartItemOption> requestOptions = CartFactory.createCartItemOptions(command.getItem().getItemId(), command.getItem().getOptions());
        updateItem.get().changeOptions(requestOptions);  //Embeddable 타입이라 쿼리 1번 호출

        CartValidator.validateCart(cart);

        //장바구니, 목록, db 동기화 여부
        cartRedisTemplate.opsForValue().set(cartKey, cart);
        redisTemplate.opsForValue().set("cart:synced:" + userId, "false");

        // DB 동기화 이벤트 발행
        CartEvent event = new CartEvent(cart, Instant.now().toString());
        cartEventProducer.sendCartEvent(event);
    }

    @Override
    @Transactional
    public void removeItem(RemoveItemCommand command) {
        String userId = command.getUserId();
        String cartItemId = command.getItem().getItemId();
        String cartKey = "cart:" + userId;

        Cart cart = getCart(cartKey);

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
        redisTemplate.opsForValue().set("cart:synced:" + userId, "false");

        // DB 동기화 이벤트 발행
        CartEvent event = new CartEvent(cart, Instant.now().toString());
        cartEventProducer.sendCartEvent(event);
    }

    @Override
    @Transactional
    public void clearCart(String userId) {
        String cartKey = "cart:" + userId;

        //장바구니 비우면 즉시 동기화
        Cart cart = cartRedisTemplate.opsForValue().get(cartKey);
        cartRepositoryPort.deleteCart(cart);

        //캐시 제거
        cartRedisTemplate.delete(cartKey);
        redisTemplate.delete("cart:synced:" + userId);
    }

    private Cart getCart(String cartKey) {
        String[] parts = cartKey.split(":");
        String userId = parts[1];
        //캐시 및 db 확인
        Cart cart = cartRedisTemplate.opsForValue().get(cartKey);
        if (cart == null) {
            List<Cart> cartList = cartRepositoryPort.findByUserIdAndStatus(userId, CartStatus.ACTIVE);
            cart = cartList.get(0);
        }
        return cart;
    }
}
