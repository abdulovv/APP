package com.app.auth_service.dtos.responses;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        LocalDate birthDate,
        String countryName,
        String role
) {
}
