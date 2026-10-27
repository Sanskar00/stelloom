package com.sanskar.project.stelloom.user.dto;

import com.sanskar.project.stelloom.common.exception.enums.Role;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserResponse(
        Long id,
        String username,
        String email,
        String fullName,
        Role role,
        String avatarUrl,
        Instant createdAt
) {}
