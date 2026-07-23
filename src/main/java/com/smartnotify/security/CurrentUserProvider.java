package com.smartnotify.security;

import com.smartnotify.feature.user.entity.User;
import com.smartnotify.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserProvider {

    private final UserService userService;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserEntityByEmail(authentication.getName());
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}