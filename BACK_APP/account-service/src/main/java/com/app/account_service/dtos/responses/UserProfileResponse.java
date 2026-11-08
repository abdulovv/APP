package com.app.account_service.dtos.responses;

import java.time.Instant;
import java.time.LocalDate;

public record UserProfileResponse(
        Long id,
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        LocalDate birthDate,
        String countryName,
        String role,
        Boolean premium,
        Instant startedAt,
        Instant expiresAt,
        Instant cancelledAt
) {
}
