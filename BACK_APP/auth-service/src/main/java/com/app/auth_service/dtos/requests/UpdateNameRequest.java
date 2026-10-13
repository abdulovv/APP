package com.app.auth_service.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateNameRequest(
        @NotBlank
        @Size(max = 50)
        String firstname,

        @NotBlank
        @Size(max = 50)
        String lastname
) {
    public UpdateNameRequest {
        firstname = NameFormatter.capitalize(firstname);
        lastname = NameFormatter.capitalize(lastname);
    }
}
