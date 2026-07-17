package com.smartnotify.feature.auth.service.impl;

import com.smartnotify.exception.DuplicateResourceException;
import com.smartnotify.exception.ResourceNotFoundException;
import com.smartnotify.feature.auth.dto.AuthResponseDTO;
import com.smartnotify.feature.auth.dto.LoginRequestDTO;
import com.smartnotify.feature.auth.dto.RegisterRequestDTO;
import com.smartnotify.feature.auth.mapper.AuthMapper;
import com.smartnotify.feature.auth.service.AuthService;
import com.smartnotify.feature.user.entity.Role;
import com.smartnotify.feature.user.entity.RoleName;
import com.smartnotify.feature.user.entity.User;
import com.smartnotify.feature.user.repository.RoleRepository;
import com.smartnotify.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already registered: " + request.getEmail());
        }

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Default role ROLE_USER not found - seeding may have failed"));

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .enabled(true)
                .build();

        User savedUser = userRepository.save(user);

        // TODO (Step 12): replace placeholder with real JWT generation via JwtUtil
        String placeholderToken = "TOKEN_WILL_BE_GENERATED_IN_STEP_12";

        return AuthMapper.toAuthResponseDTO(savedUser, placeholderToken);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        // TODO (Step 12): replace placeholder with real JWT generation via JwtUtil
        String placeholderToken = "TOKEN_WILL_BE_GENERATED_IN_STEP_12";

        return AuthMapper.toAuthResponseDTO(user, placeholderToken);
    }
}