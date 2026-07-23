package com.smartnotify.feature.notification.dto;

import com.smartnotify.feature.notification.entity.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BroadcastNotificationRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 150)
    private String title;

    @NotBlank(message = "Message is required")
    @Size(max = 1000)
    private String message;

    @NotNull(message = "Notification type is required")
    private NotificationType type;
}