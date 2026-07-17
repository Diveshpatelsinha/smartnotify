package com.smartnotify.feature.auth.service;

import com.smartnotify.feature.auth.dto.AuthResponseDTO;
import com.smartnotify.feature.auth.dto.LoginRequestDTO;
import com.smartnotify.feature.auth.dto.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);
}