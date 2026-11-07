package com.sanskar.project.stelloom.auth.dto;

import com.sanskar.project.stelloom.common.exception.enums.Role;

public record AuthUser(Long id, Long organizationId, Role role) {}