package com.github.mingyu.websocket.service;

/*
* 고객 관점 - 서버가 고객에게 라이더의 실시간 정보를 push
* 라이더 관점 - 서버에게 실시간 위치 polling
* */

import com.github.mingyu.websocket.dto.DeliveryStatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendStatusUpdate(DeliveryStatusMessage message) {
        messagingTemplate.convertAndSend("/topic/delivery-status/" + message.getOrderId(), message);
    }
}
