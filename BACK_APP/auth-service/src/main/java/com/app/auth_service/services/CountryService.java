package com.app.auth_service.services;

import com.app.auth_service.dtos.common.CountryDTO;
import com.app.auth_service.exceptions.CountryNotFoundException;
import com.app.auth_service.mappers.CountryMapper;
import com.app.auth_service.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Transactional(readOnly = true)
    public List<CountryDTO> getAllCountries() {
        return countryMapper.toDtoList(countryRepository.findAllByOrderByNameAsc());
    }

    @Transactional(readOnly = true)
    public CountryDTO getCountryById(Long id) {
        return countryRepository.findById(id)
                .map(countryMapper::toDto)
                .orElseThrow(CountryNotFoundException::new);
    }
}
