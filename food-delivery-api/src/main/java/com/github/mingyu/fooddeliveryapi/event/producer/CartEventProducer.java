package com.github.mingyu.fooddeliveryapi.event.producer;

import com.github.mingyu.fooddeliveryapi.event.dto.CartEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartEventProducer {

    @Qualifier("cartEventKafkaTemplate")
    private final KafkaTemplate<String, CartEvent> kafkaTemplate;

    public void sendCartEvent(CartEvent event){
        kafkaTemplate.send("cart-events", event.getUserId().toString(), event);
    }
}
