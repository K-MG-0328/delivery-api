package com.github.mingyu.websocket.event;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.websocket.config.DeliveryWebSocketHandler;
import com.github.mingyu.websocket.dto.DeliveryStatusMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryStatusEventConsumer {
    private final ObjectMapper objectMapper;
    private final DeliveryWebSocketHandler webSocketHandler;

    @KafkaListener(topics = "delivery-status", groupId = "delivery-update-service")
    public void listen(ConsumerRecord<String, String> record) throws JsonProcessingException, JsonMappingException {
        Mono.just(record.value())
                .map(value -> {
                    try{
                        return objectMapper.readValue(value, DeliveryStatusMessage.class);
                    }catch (JsonProcessingException e){
                        throw new RuntimeException("Json 변환 실패", e);
                    }
                 })
                .doOnNext(webSocketHandler::publish)
                .doOnError(e -> log.error("배송 상태 처리 실패", e))
                .subscribe();
    }
}
