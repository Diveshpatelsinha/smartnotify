package com.smartnotify.feature.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String role;
    private boolean enabled;
    private LocalDateTime createdAt;
}