package com.smartnotify.feature.user.mapper;

import com.smartnotify.feature.user.dto.UserResponseDTO;
import com.smartnotify.feature.user.entity.User;

public class UserMapper {

    private UserMapper() {}

    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) return null;
        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole() != null ? user.getRole().getName().name() : null)
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }
}