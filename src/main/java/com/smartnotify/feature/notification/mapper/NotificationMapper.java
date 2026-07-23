package com.smartnotify.feature.notification.mapper;

import com.smartnotify.feature.notification.dto.NotificationResponseDTO;
import com.smartnotify.feature.notification.entity.Notification;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {

    private NotificationMapper() {}

    public static NotificationResponseDTO toResponseDTO(Notification n) {
        if (n == null) return null;
        return NotificationResponseDTO.builder()
                .id(n.getId())
                .title(n.getTitle())
                .message(n.getMessage())
                .type(n.getType())
                .read(n.isRead())
                .recipientId(n.getRecipient() != null ? n.getRecipient().getId() : null)
                .recipientName(n.getRecipient() != null ? n.getRecipient().getFullName() : null)
                .createdAt(n.getCreatedAt())
                .build();
    }

    public static List<NotificationResponseDTO> toResponseDTOList(List<Notification> list) {
        if (list == null) return List.of();
        return list.stream().map(NotificationMapper::toResponseDTO).collect(Collectors.toList());
    }
}