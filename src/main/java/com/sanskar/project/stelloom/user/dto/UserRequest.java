package com.sanskar.project.stelloom.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @Size(min=3, max=100) String fullName
) {

}
