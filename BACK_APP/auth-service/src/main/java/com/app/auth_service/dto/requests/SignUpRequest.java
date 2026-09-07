package com.app.auth_service.dto.requests;

import jakarta.validation.constraints.*;

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
        @Size(max = 50)
        String email,

        @NotBlank
        @Size(min = 7, max = 15)
        @Pattern(regexp = "^\\+\\d{7,15}$")
        String phoneNumber,

        @NotBlank
        @Size(min = 5, max = 10)
        String password,

        @NotNull
        @Past
        LocalDate birthDate,

        @NotNull
        Long countryId

) {}
