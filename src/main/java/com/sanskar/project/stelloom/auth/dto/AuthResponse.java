package com.sanskar.project.stelloom.auth.dto;

import com.sanskar.project.stelloom.user.dto.UserResponse;
import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresInSeconds,
        UserResponse user
) {}
