package com.smartnotify.feature.notification.service;

import com.smartnotify.feature.notification.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    Page<NotificationResponseDTO> getUserNotifications(Long userId, Pageable pageable);

    NotificationResponseDTO getUserNotificationById(Long userId, Long notificationId);

    NotificationResponseDTO markAsRead(Long userId, Long notificationId);

    void deleteUserNotification(Long userId, Long notificationId);

    DashboardStatsDTO getDashboardStats(Long userId);

    NotificationResponseDTO sendToUser(SendNotificationRequestDTO request);

    void broadcast(BroadcastNotificationRequestDTO request);

    Page<NotificationResponseDTO> getAllNotifications(Pageable pageable);

    void adminDeleteNotification(Long notificationId);
}