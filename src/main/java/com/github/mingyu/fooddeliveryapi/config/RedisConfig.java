package com.github.mingyu.fooddeliveryapi.config;

import com.github.mingyu.fooddeliveryapi.enums.DeliveryStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, DeliveryStatus> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, DeliveryStatus> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(DeliveryStatus.class));
        return template;
    }
}
