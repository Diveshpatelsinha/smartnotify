package com.smartnotify.feature.auth.mapper;

import com.smartnotify.feature.auth.dto.AuthResponseDTO;
import com.smartnotify.feature.user.entity.User;

public class AuthMapper {

    private AuthMapper() {
        // prevent instantiation - utility class
    }

    public static AuthResponseDTO toAuthResponseDTO(User user, String token) {
        if (user == null) {
            return null;
        }

        return AuthResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole() != null ? user.getRole().getName().name() : null)
                .build();
    }
}