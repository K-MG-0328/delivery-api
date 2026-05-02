package com.github.mingyu.websocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mingyu.common.event.DeliveryStatusMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryWebSocketHandler implements WebSocketHandler {

    private final Sinks.Many<DeliveryStatusMessage> sink = Sinks.many().multicast().onBackpressureBuffer();
    private final JwtValidator jwtValidator;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Map<String, String> params = parseQuery(session.getHandshakeInfo().getUri());

        String token = params.get("token");
        if (!jwtValidator.isValid(token)) {
            return session.close(CloseStatus.POLICY_VIOLATION.withReason("Invalid or missing JWT"));
        }

        String orderId = params.get("orderId");
        if (orderId == null || orderId.isBlank()) {
            return session.close(CloseStatus.BAD_DATA.withReason("orderId required"));
        }

        Flux<WebSocketMessage> messageFlux = sink.asFlux()
                .filter(msg -> orderId.equals(msg.orderId()))
                .map(msg -> session.textMessage(serialize(msg)));

        return session.send(messageFlux).and(session.receive().then());
    }

    private Map<String, String> parseQuery(URI uri) {
        Map<String, String> map = new HashMap<>();
        String query = uri.getRawQuery();
        if (query == null || query.isEmpty()) return map;
        for (String pair : query.split("&")) {
            int eq = pair.indexOf('=');
            if (eq > 0) {
                map.put(pair.substring(0, eq), pair.substring(eq + 1));
            }
        }
        return map;
    }

    private String serialize(DeliveryStatusMessage message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            throw new RuntimeException("Serialization error", e);
        }
    }

    public void publish(DeliveryStatusMessage message) {
        sink.tryEmitNext(message);
    }
}
