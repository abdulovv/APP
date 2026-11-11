package com.app.account_service.dtos.requests;

import com.app.account_service.utils.NameFormatter;
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
