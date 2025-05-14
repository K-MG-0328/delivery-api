package com.github.mingyu.websocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.websocket.dto.DeliveryStatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component
@RequiredArgsConstructor
public class DeliveryWebSocketHandler implements WebSocketHandler {

    private final Sinks.Many<DeliveryStatusMessage> sink = Sinks.many().multicast().onBackpressureBuffer();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String orderId = session.getHandshakeInfo().getUri().getQuery().split("=")[1];

        Flux<WebSocketMessage> messageFlux = sink.asFlux()
                .filter(msg -> msg.getOrderId().toString().equals(orderId))
                .map(msg -> session.textMessage(serialize(msg)));

        return session.send(messageFlux).and(session.receive().then());
    }

    private String serialize(DeliveryStatusMessage message) {
        try {
            return new ObjectMapper().writeValueAsString(message);
        } catch (Exception e) {
            throw new RuntimeException("Serialization error", e);
        }
    }

    public void publish(DeliveryStatusMessage message) {
        sink.tryEmitNext(message);
    }
}
