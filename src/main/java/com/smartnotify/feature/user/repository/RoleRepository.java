package com.smartnotify.feature.user.repository;

import com.smartnotify.feature.user.entity.Role;
import com.smartnotify.feature.user.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}