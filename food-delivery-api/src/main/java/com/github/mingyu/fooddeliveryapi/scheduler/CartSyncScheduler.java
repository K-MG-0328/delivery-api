package com.github.mingyu.fooddeliveryapi.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.fooddeliveryapi.domain.CartSync;
import com.github.mingyu.fooddeliveryapi.entity.Cart;
import com.github.mingyu.fooddeliveryapi.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartSyncScheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final CartRepository cartRepository;

    @Scheduled(fixedRate = 600000) //1시간 마다 동기화
    public void syncCart() {
        log.info("Starting cart sync...");

        // 캐시된 카트 데이터 가져오기.
        Set<String> keys = redisTemplate.keys("cart:*");
        List<Cart> cartList = new ArrayList<>();

        // DB와 동기화 및 캐시 업데이트
        for(String key : keys) {
            String cartSyncJson = redisTemplate.opsForValue().get(key);

            try {
                CartSync cartSync = objectMapper.readValue(cartSyncJson, CartSync.class);

                //DB 동기화 목록은 제외
                if(cartSync.isDbSync()){
                   break;
                }

                cartSync.setDbSync(true);
                String updateJson = objectMapper.writeValueAsString(cartSync);
                redisTemplate.opsForValue().set(key, updateJson);
                cartList.add(cartSync.getCart());

            }catch (JsonProcessingException e){
                new RuntimeException("동기화 실패 ", e);
            }
        }

        //데이터 저장
        cartRepository.saveAll(cartList);
    }
}
