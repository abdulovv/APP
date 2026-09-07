package com.app.auth_service.dto.responses;

public record CountryResponse(
        Long id,
        String name,
        Boolean availability,
        String phoneCode,
        String currencySymbol,
        Float exchangeRate
) {
}
