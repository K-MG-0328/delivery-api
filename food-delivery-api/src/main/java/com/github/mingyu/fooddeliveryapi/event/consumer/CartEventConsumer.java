package com.github.mingyu.fooddeliveryapi.event.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.entity.Cart;
import com.github.mingyu.fooddeliveryapi.entity.CartItem;
import com.github.mingyu.fooddeliveryapi.enums.CartStatus;
import com.github.mingyu.fooddeliveryapi.event.dto.CartEvent;
import com.github.mingyu.fooddeliveryapi.repository.CartItemRepository;
import com.github.mingyu.fooddeliveryapi.repository.CartRepository;
import com.github.mingyu.fooddeliveryapi.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartEventConsumer {

    private final RedisTemplate<String, String> redisTemplate;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ObjectMapper objectMapper;

    private static final String EVENT_QUEUE_PREFIX = "cart:events:queue:";
    private final CartService cartService;

    @KafkaListener(topics = "cart-events", groupId = "cart-group")
    public void consume(CartEvent event) throws Exception {
        Cart cart = event.getCart();
        List<CartItem> cartItems = event.getCartItems();
        String userId = cart.getUserId().toString();
        String syncedKey = "cart:synced:" + userId;

        // 동기화 여부 확인
        String dbSync = redisTemplate.opsForValue().get(syncedKey);
        if ("true".equals(dbSync) || dbSync == null) {
            return;
        }

        // 즉시 동기화
        if (CartStatus.PAID.equals(cart.getStatus())) {
            if (cart != null && cart.getUserId() != null) {
                cart.setStatus(CartStatus.PAID);
                cartRepository.save(cart);
                cartItemRepository.saveAll(cartItems);
                cartService.clearCart(cart.getUserId());
            }
        } else {
            // 이벤트를 Redis 리스트에 추가 (배치 처리 대기)
            String eventQueueKey = EVENT_QUEUE_PREFIX + userId;
            String eventJson = objectMapper.writeValueAsString(event);
            redisTemplate.opsForList().rightPush(eventQueueKey, eventJson);
        }
    }


    // 배치 처리 스케줄러
    @Scheduled(fixedRate = 3600000) // 1시간
    @SchedulerLock(name = "cartSyncTask", lockAtMostFor = "10m", lockAtLeastFor = "1m")
    public void processBatchEvents() {

        // Redis에서 모든 이벤트 큐 키 조회
        Set<String> eventQueueKeys = redisTemplate.keys(EVENT_QUEUE_PREFIX + "*");
        if (eventQueueKeys == null) return;

        for (String eventQueueKey : eventQueueKeys) {
            String userId = eventQueueKey.substring(EVENT_QUEUE_PREFIX.length());
            String syncedKey = "cart:synced:" + userId;

            // 동기화 여부 확인
            String dbSync = redisTemplate.opsForValue().get(syncedKey);
            if ("true".equals(dbSync) || dbSync == null) {
                redisTemplate.delete(eventQueueKey); // 큐 정리
                continue;
            }

            // 최신 이벤트만 처리 (마지막 이벤트 기준)
            String eventJson = redisTemplate.opsForList().rightPop(eventQueueKey);
            if (eventJson == null || eventJson.isEmpty()) continue;

            //Cart, cartItem 저장
            try {
                CartEvent cartEvent = objectMapper.readValue(eventJson, CartEvent.class);
                Cart cart = cartEvent.getCart();
                List<CartItem> cartItems = cartEvent.getCartItems();
                if (cart != null ) {
                    cartRepository.save(cart);
                    cartItemRepository.saveAll(cartItems);
                    redisTemplate.opsForValue().set(syncedKey, "true");
                    redisTemplate.delete(eventQueueKey); // 큐 정리
                }
            } catch (JsonProcessingException e) {
                log.error("Batch sync failed for user: {}", userId, e);
            }
        }
    }
}
