package com.app.auth_service.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateEmailRequest(
        @NotBlank
        @Email
        @Size(max = 254)
        String email
) {
    public UpdateEmailRequest {
        email = email != null ? email.trim().toLowerCase() : null;
    }
}
