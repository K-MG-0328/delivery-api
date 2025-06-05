package com.github.mingyu.fooddeliveryapi.common.config;

import com.github.mingyu.fooddeliveryapi.cart.domain.Cart;
import com.github.mingyu.fooddeliveryapi.cart.domain.CartItem;
import com.github.mingyu.fooddeliveryapi.domain.delivery.domain.DeliveryState;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> RedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, Cart> cartRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Cart> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Cart.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, List<CartItem>> cartItemRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, List<CartItem>> template = new RedisTemplate<>();
        // key는 문자열
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // value는 GenericJackson2JsonRedisSerializer
        GenericJackson2JsonRedisSerializer gjSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(gjSerializer);
        template.setHashValueSerializer(gjSerializer);

        template.afterPropertiesSet();
        return template;
    }


    @Bean
    public RedisTemplate<String, DeliveryState> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, DeliveryState> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(DeliveryState.class));
        return template;
    }

    @Bean
    public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
        return new RedisLockProvider(connectionFactory);
    }
}
