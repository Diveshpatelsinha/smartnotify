package com.smartnotify.feature.notification.service;

import com.smartnotify.feature.notification.dto.NotificationResponseDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationPushService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationPushService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void pushToUser(String email, NotificationResponseDTO notification) {
        messagingTemplate.convertAndSendToUser(email, "/queue/notifications", notification);
    }

    public void pushBroadcast(NotificationResponseDTO notification) {
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }
}