package com.app.auth_service.dto.mappers;

import com.app.auth_service.dto.responses.CountryResponse;
import com.app.auth_service.entities.Country;

public class CountryMapper {
    public static CountryResponse fromEntityToResponse(Country country) {
        return new CountryResponse(
                country.getId(),
                country.getName(),
                country.getAvailability(),
                country.getPhoneCode(),
                country.getCurrencySymbol(),
                country.getExchangeRate()
        );
    }
}
