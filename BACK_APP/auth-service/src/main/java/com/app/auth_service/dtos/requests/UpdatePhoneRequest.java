package com.app.auth_service.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdatePhoneRequest(
        @NotBlank
        @Pattern(regexp = "^\\+\\d{7,15}$")
        String phoneNumber
) {
}
