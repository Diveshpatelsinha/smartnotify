package com.smartnotify.feature.notification.controller;

import com.smartnotify.common.ApiResponse;
import com.smartnotify.feature.notification.dto.DashboardStatsDTO;
import com.smartnotify.feature.notification.dto.NotificationResponseDTO;
import com.smartnotify.feature.notification.service.NotificationService;
import com.smartnotify.security.CurrentUserProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "User notification endpoints")
public class NotificationController {

    private final NotificationService notificationService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping
    @Operation(summary = "Get paginated notifications for the logged-in user")
    public ResponseEntity<ApiResponse<Page<NotificationResponseDTO>>> getMyNotifications(
            @PageableDefault(size = 10) Pageable pageable) {

        Long userId = currentUserProvider.getCurrentUserId();
        Page<NotificationResponseDTO> notifications = notificationService.getUserNotifications(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success("Notifications fetched successfully", notifications));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single notification by ID (must belong to the logged-in user)")
    public ResponseEntity<ApiResponse<NotificationResponseDTO>> getNotificationById(
            @PathVariable Long id) {

        Long userId = currentUserProvider.getCurrentUserId();
        NotificationResponseDTO notification = notificationService.getUserNotificationById(userId, id);
        return ResponseEntity.ok(ApiResponse.success("Notification fetched successfully", notification));
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Mark a notification as read")
    public ResponseEntity<ApiResponse<NotificationResponseDTO>> markAsRead(
            @PathVariable Long id) {

        Long userId = currentUserProvider.getCurrentUserId();
        NotificationResponseDTO notification = notificationService.markAsRead(userId, id);
        return ResponseEntity.ok(ApiResponse.success("Notification marked as read", notification));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a notification (must belong to the logged-in user)")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(
            @PathVariable Long id) {

        Long userId = currentUserProvider.getCurrentUserId();
        notificationService.deleteUserNotification(userId, id);
        return ResponseEntity.ok(ApiResponse.success("Notification deleted successfully", null));
    }

    @GetMapping("/dashboard/stats")
    @Operation(summary = "Get dashboard stats (total, read, unread) for the logged-in user")
    public ResponseEntity<ApiResponse<DashboardStatsDTO>> getDashboardStats() {
        Long userId = currentUserProvider.getCurrentUserId();
        DashboardStatsDTO stats = notificationService.getDashboardStats(userId);
        return ResponseEntity.ok(ApiResponse.success("Dashboard stats fetched successfully", stats));
    }
}