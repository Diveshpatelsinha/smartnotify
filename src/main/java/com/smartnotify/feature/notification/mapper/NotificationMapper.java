package com.smartnotify.feature.notification.mapper;

import com.smartnotify.feature.notification.dto.NotificationResponseDTO;
import com.smartnotify.feature.notification.entity.Notification;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {

    private NotificationMapper() {
        // prevent instantiation - utility class
    }

    public static NotificationResponseDTO toResponseDTO(Notification notification) {
        if (notification == null) {
            return null;
        }

        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getType())
                .read(notification.isRead())
                .recipientId(notification.getRecipient() != null ? notification.getRecipient().getId() : null)
                .recipientName(notification.getRecipient() != null ? notification.getRecipient().getFullName() : null)
                .createdAt(notification.getCreatedAt())
                .build();
    }

    public static List<NotificationResponseDTO> toResponseDTOList(List<Notification> notifications) {
        if (notifications == null) {
            return List.of();
        }

        return notifications.stream()
                .map(NotificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}