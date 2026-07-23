package com.smartnotify.feature.user.service;

import com.smartnotify.feature.user.dto.UserResponseDTO;
import com.smartnotify.feature.user.entity.User;

public interface UserService {
    UserResponseDTO getProfile(Long userId);
    User getUserEntityById(Long userId);
    User getUserEntityByEmail(String email);
}