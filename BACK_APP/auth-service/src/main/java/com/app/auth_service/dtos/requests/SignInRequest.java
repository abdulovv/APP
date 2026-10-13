package com.app.auth_service.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank
        String uniqueField,

        @NotBlank
        String password
) {
}
