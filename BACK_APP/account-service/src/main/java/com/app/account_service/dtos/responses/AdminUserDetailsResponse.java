package com.app.account_service.dtos.responses;

import java.time.Instant;
import java.time.LocalDate;

public record AdminUserDetailsResponse (
        Long id,
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        LocalDate birthDate,
        String countryName,
        String role,
        boolean premium,
        Instant premiumStartedAt,
        Instant premiumExpiresAt
){

}
