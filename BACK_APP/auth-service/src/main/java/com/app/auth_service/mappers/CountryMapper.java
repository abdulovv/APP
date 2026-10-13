package com.app.auth_service.mappers;

import com.app.auth_service.dtos.common.CountryDTO;
import com.app.auth_service.entities.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDTO toDto(Country country);

    List<CountryDTO> toDtoList(List<Country> countries);
}
