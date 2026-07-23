package com.smartnotify.feature.notification.repository;

import com.smartnotify.feature.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByRecipientIdOrderByCreatedAtDesc(Long recipientId, Pageable pageable);
    long countByRecipientIdAndReadFalse(Long recipientId);
    long countByRecipientIdAndReadTrue(Long recipientId);
    long countByRecipientId(Long recipientId);
}