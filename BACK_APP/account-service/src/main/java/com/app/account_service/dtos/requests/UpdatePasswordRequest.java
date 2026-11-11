package com.app.account_service.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(
        @NotBlank
        String currentPassword,

        @NotBlank
        @Size(min = 8, max = 64)
        String newPassword,

        @NotBlank
        @Pattern(regexp = "^\\d{6}$")
        String code
) {
}
