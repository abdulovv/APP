package com.app.auth_service.mappers;

import com.app.auth_service.dtos.common.CountryDTO;
import com.app.auth_service.dtos.responses.CountryResponse;
import com.app.auth_service.entities.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDTO toDto(Country country);
    Country toEntity(CountryDTO dto);
}
