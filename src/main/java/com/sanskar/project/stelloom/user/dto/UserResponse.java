package com.sanskar.project.stelloom.dto;

import com.sanskar.project.stelloom.entity.enums.Role;
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
