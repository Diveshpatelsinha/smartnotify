package com.smartnotify.feature.auth.controller;

import com.smartnotify.common.ApiResponse;
import com.smartnotify.feature.auth.dto.AuthResponseDTO;
import com.smartnotify.feature.auth.dto.LoginRequestDTO;
import com.smartnotify.feature.auth.dto.RegisterRequestDTO;
import com.smartnotify.feature.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register and login endpoints")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user account")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", authService.register(request)));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user and receive a JWT token")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Login successful", authService.login(request)));
    }
}