package com.sanskar.project.stelloom.user.dto;

import com.sanskar.project.stelloom.common.exception.enums.Role;
import com.sanskar.project.stelloom.organization.Organization;
import com.sanskar.project.stelloom.organization.dto.OrganizationResponse;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserResponse(
        Long id,
        String username,
        String email,
        String fullName,
        OrganizationResponse organization,
        Role role,
        String avatarUrl,
        Instant createdAt
) {}
