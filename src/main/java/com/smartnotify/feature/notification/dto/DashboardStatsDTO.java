package com.smartnotify.feature.notification.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsDTO {
    private long totalNotifications;
    private long readNotifications;
    private long unreadNotifications;
}