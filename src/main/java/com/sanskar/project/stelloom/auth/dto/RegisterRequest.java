package com.sanskar.project.stelloom.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.*;
import lombok.Builder;

public record RegisterRequest (
        @Pattern(
                regexp = "^[A-Za-z][A-Za-z0-9_]{2,49}$",
                message = "Username must start with a letter, be 3-50 characters, and contain only letters, digits, and underscores"
        )@NotBlank @Size(min=3, max=50) String username,
        @NotBlank @Email String email,
        @NotBlank @Size(min=8, max=72) String password,
        @NotBlank @Size(max=100) String fullName
){}
