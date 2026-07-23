package com.smartnotify.feature.user.controller;

import com.smartnotify.common.ApiResponse;
import com.smartnotify.feature.user.dto.UserResponseDTO;
import com.smartnotify.feature.user.service.UserService;
import com.smartnotify.security.CurrentUserProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User profile endpoints")
public class UserController {

    private final UserService userService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping("/me")
    @Operation(summary = "Get the logged-in user's profile")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getMyProfile() {
        Long userId = currentUserProvider.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Profile fetched successfully", userService.getProfile(userId)));
    }
}