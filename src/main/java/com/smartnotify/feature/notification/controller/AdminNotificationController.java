package com.smartnotify.feature.notification.controller;

import com.smartnotify.common.ApiResponse;
import com.smartnotify.feature.notification.dto.*;
import com.smartnotify.feature.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/notifications")
@RequiredArgsConstructor
@Tag(name = "Admin Notifications", description = "Admin-only notification management endpoints")
public class AdminNotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    @Operation(summary = "Send a notification to a single user")
    public ResponseEntity<ApiResponse<NotificationResponseDTO>> sendToUser(
            @Valid @RequestBody SendNotificationRequestDTO request) {

        NotificationResponseDTO response = notificationService.sendToUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Notification sent successfully", response));
    }

    @PostMapping("/broadcast")
    @Operation(summary = "Broadcast a notification to all users")
    public ResponseEntity<ApiResponse<Void>> broadcast(
            @Valid @RequestBody BroadcastNotificationRequestDTO request) {

        notificationService.broadcast(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Notification broadcast to all users", null));
    }

    @GetMapping
    @Operation(summary = "Get all notifications across all users (paginated)")
    public ResponseEntity<ApiResponse<Page<NotificationResponseDTO>>> getAllNotifications(
            @PageableDefault(size = 20) Pageable pageable) {

        Page<NotificationResponseDTO> notifications = notificationService.getAllNotifications(pageable);
        return ResponseEntity.ok(ApiResponse.success("All notifications fetched successfully", notifications));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete any notification by ID (admin override)")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(@PathVariable Long id) {
        notificationService.adminDeleteNotification(id);
        return ResponseEntity.ok(ApiResponse.success("Notification deleted successfully", null));
    }
}