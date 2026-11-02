package com.app.account_service.dtos.responses;

import com.app.account_service.entities.PremiumSubscription;

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
        Boolean premium
) {
}
