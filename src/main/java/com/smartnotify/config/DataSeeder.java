package com.smartnotify.config;

import com.smartnotify.feature.user.entity.Role;
import com.smartnotify.feature.user.entity.RoleName;
import com.smartnotify.feature.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        seedRoleIfMissing(RoleName.ROLE_ADMIN);
        seedRoleIfMissing(RoleName.ROLE_USER);
    }

    private void seedRoleIfMissing(RoleName roleName) {
        roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(Role.builder().name(roleName).build()));
    }
}