package com.smartnotify.feature.notification.service.impl;

import com.smartnotify.exception.ResourceNotFoundException;
import com.smartnotify.feature.notification.dto.*;
import com.smartnotify.feature.notification.entity.Notification;
import com.smartnotify.feature.notification.mapper.NotificationMapper;
import com.smartnotify.feature.notification.repository.NotificationRepository;
import com.smartnotify.feature.notification.service.NotificationPushService;
import com.smartnotify.feature.notification.service.NotificationService;
import com.smartnotify.feature.user.entity.User;
import com.smartnotify.feature.user.repository.UserRepository;
import com.smartnotify.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final NotificationPushService notificationPushService;

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponseDTO> getUserNotifications(Long userId, Pageable pageable) {
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId, pageable)
                .map(NotificationMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponseDTO getUserNotificationById(Long userId, Long notificationId) {
        return NotificationMapper.toResponseDTO(getOwnedNotification(userId, notificationId));
    }

    @Override
    public NotificationResponseDTO markAsRead(Long userId, Long notificationId) {
        Notification notification = getOwnedNotification(userId, notificationId);
        notification.setRead(true);
        return NotificationMapper.toResponseDTO(notification);
    }

    @Override
    public void deleteUserNotification(Long userId, Long notificationId) {
        notificationRepository.delete(getOwnedNotification(userId, notificationId));
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDTO getDashboardStats(Long userId) {
        return DashboardStatsDTO.builder()
                .totalNotifications(notificationRepository.countByRecipientId(userId))
                .readNotifications(notificationRepository.countByRecipientIdAndReadTrue(userId))
                .unreadNotifications(notificationRepository.countByRecipientIdAndReadFalse(userId))
                .build();
    }

    @Override
    public NotificationResponseDTO sendToUser(SendNotificationRequestDTO request) {
        User recipient = userService.getUserEntityById(request.getRecipientId());

        Notification notification = Notification.builder()
                .title(request.getTitle())
                .message(request.getMessage())
                .type(request.getType())
                .read(false)
                .recipient(recipient)
                .build();

        Notification saved = notificationRepository.save(notification);
        NotificationResponseDTO responseDTO = NotificationMapper.toResponseDTO(saved);

        notificationPushService.pushToUser(recipient.getEmail(), responseDTO);
        return responseDTO;
    }

    @Override
    public void broadcast(BroadcastNotificationRequestDTO request) {
        List<User> allUsers = userRepository.findAll();

        List<Notification> notifications = allUsers.stream()
                .map(user -> Notification.builder()
                        .title(request.getTitle())
                        .message(request.getMessage())
                        .type(request.getType())
                        .read(false)
                        .recipient(user)
                        .build())
                .toList();

        List<Notification> saved = notificationRepository.saveAll(notifications);

        if (!saved.isEmpty()) {
            notificationPushService.pushBroadcast(NotificationMapper.toResponseDTO(saved.get(0)));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponseDTO> getAllNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable).map(NotificationMapper::toResponseDTO);
    }

    @Override
    public void adminDeleteNotification(Long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new ResourceNotFoundException("Notification not found with id: " + notificationId);
        }
        notificationRepository.deleteById(notificationId);
    }

    private Notification getOwnedNotification(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));

        if (!notification.getRecipient().getId().equals(userId)) {
            throw new ResourceNotFoundException("Notification not found with id: " + notificationId);
        }
        return notification;
    }
}