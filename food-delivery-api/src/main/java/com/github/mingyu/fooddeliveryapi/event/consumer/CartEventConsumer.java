package com.github.mingyu.fooddeliveryapi.event.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.entity.Cart;
import com.github.mingyu.fooddeliveryapi.enums.OrderStatus;
import com.github.mingyu.fooddeliveryapi.event.dto.CartEvent;
import com.github.mingyu.fooddeliveryapi.repository.CartRepository;
import com.github.mingyu.fooddeliveryapi.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ObjectMapper objectMapper;

    private static final String EVENT_QUEUE_PREFIX = "cart:events:queue:";
    private final CartService cartService;

    @KafkaListener(topics = "cart-events", groupId = "cart-group")
    public void consume(CartEvent event) throws Exception {
        String userId = event.getUserId();
        String key = "cart:user:" + userId;
        String syncedKey = "cart:synced:user:" + userId;

        // 동기화 여부 확인
        if ("true".equals(redisTemplate.opsForValue().get(syncedKey))) {
            return;
        }

        // 즉시 동기화
        if (OrderStatus.PAID.equals(event.getStatus())) {
            String cartJson = redisTemplate.opsForValue().get(key);
            if (cartJson != null) {
                try {
                    Cart cart = objectMapper.readValue(cartJson, Cart.class);
                    if (cart != null && cart.getUserId() != null) {
                        cart.setStatus(OrderStatus.PAID);
                        cartRepository.save(cart);
                        cartService.clearCart(cart.getUserId());
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("장바구니 동기화 실패", e);
                }
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
    public void processBatchEvents() {
        log.info("Starting batch event processing...");

        // Redis에서 모든 이벤트 큐 키 조회
        Set<String> eventQueueKeys = redisTemplate.keys(EVENT_QUEUE_PREFIX + "*");
        if (eventQueueKeys == null) return;

        for (String eventQueueKey : eventQueueKeys) {
            String userId = eventQueueKey.substring(EVENT_QUEUE_PREFIX.length());
            String key = "cart:user:" + userId;
            String syncedKey = "cart:synced:user:" + userId;

            // 이미 동기화된 경우 스킵
            if ("true".equals(redisTemplate.opsForValue().get(syncedKey))) {
                redisTemplate.delete(eventQueueKey); // 큐 정리
                continue;
            }

            // 최신 이벤트만 처리 (마지막 이벤트 기준)
            List<String> eventJsons = redisTemplate.opsForList().range(eventQueueKey, 0, -1);
            if (eventJsons == null || eventJsons.isEmpty()) continue;

            // Redis에서 Cart 조회 및 저장
            String cartJson = redisTemplate.opsForValue().get(key);
            if (cartJson != null) {
                try {
                    Cart cart = objectMapper.readValue(cartJson, Cart.class);
                    if (cart != null && cart.getUserId() != null) {
                        cartRepository.save(cart);
                        redisTemplate.opsForValue().set(syncedKey, "true");
                        redisTemplate.delete(eventQueueKey); // 큐 정리
                        log.info("Batch synced cart for user {}", userId);
                    }
                } catch (JsonProcessingException e) {
                    log.error("Batch sync failed for user: {}", userId, e);
                }
            }
        }
    }
}
