package com.smartnotify.feature.user.service.impl;

import com.smartnotify.exception.ResourceNotFoundException;
import com.smartnotify.feature.user.dto.UserResponseDTO;
import com.smartnotify.feature.user.entity.User;
import com.smartnotify.feature.user.mapper.UserMapper;
import com.smartnotify.feature.user.repository.UserRepository;
import com.smartnotify.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO getProfile(Long userId) {
        return UserMapper.toResponseDTO(getUserEntityById(userId));
    }

    @Override
    public User getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    public User getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}