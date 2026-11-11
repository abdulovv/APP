package com.app.account_service.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SendCodeRequest(
        @NotBlank
        @Email
        @Size(max = 254)
        String email
) {
    public SendCodeRequest {
        email = email != null ? email.trim().toLowerCase() : null;
    }
}
