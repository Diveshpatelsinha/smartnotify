package com.smartnotify.feature.notification.dto;

import com.smartnotify.feature.notification.entity.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDTO {
    private Long id;
    private String title;
    private String message;
    private NotificationType type;
    private boolean read;
    private Long recipientId;
    private String recipientName;
    private LocalDateTime createdAt;
}