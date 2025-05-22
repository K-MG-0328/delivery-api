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

    /* Cart 변경 사항에 대해서 메시지 발행 */
    public void sendCartEvent(CartEvent event){
        String userId = event.getCart().getUserId().toString();
        kafkaTemplate.send("cart-events", userId, event);
    }
}
