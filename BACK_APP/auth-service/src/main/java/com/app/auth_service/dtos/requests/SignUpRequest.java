package com.app.auth_service.dtos.requests;

import com.app.auth_service.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

public record SignUpRequest(
        @NotBlank
        @Size(max = 50)
        String firstname,

        @NotBlank
        @Size(max = 50)
        String lastname,

        @NotBlank
        @Email
        @Size(max = 254)
        String email,

        @NotBlank
        @Pattern(regexp = "^\\+\\d{7,15}$")
        String phoneNumber,

        @NotBlank
        @Size(min = 8, max = 64)
        String password,

        @NotNull
        @Past
        LocalDate birthDate,

        @NotNull
        Long countryId
) {
    public SignUpRequest {
        firstname = NameFormatter.capitalize(firstname);
        lastname = NameFormatter.capitalize(lastname);
        email = email != null ? email.trim().toLowerCase() : null;
    }
}
