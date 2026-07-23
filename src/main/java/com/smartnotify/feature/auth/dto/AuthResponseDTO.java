package com.smartnotify.feature.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
    private String token;
    private String tokenType;
    private Long userId;
    private String fullName;
    private String email;
    private String role;
}