package com.app.account_service.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdatePhoneRequest(
        @NotBlank
        @Pattern(regexp = "^\\+\\d{7,15}$", message = "Phone must start with + and contain 7–15 digits")
        String phoneNumber
) {
}
