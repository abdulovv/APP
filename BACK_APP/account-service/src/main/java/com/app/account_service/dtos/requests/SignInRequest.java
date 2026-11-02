package com.app.account_service.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank
        String login,

        @NotBlank
        String password
) {
}
