package com.example.codearena_backend.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void send(Long sessionId, Object payload) {
        messagingTemplate.convertAndSend("/topic/game/" + sessionId, payload);
    }
}
