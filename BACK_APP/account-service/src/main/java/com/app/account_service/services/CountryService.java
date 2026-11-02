package com.app.account_service.services;

import com.app.account_service.dtos.responses.CountryResponse;
import com.app.account_service.exceptions.CountryNotFoundException;
import com.app.account_service.mappers.CountryMapper;
import com.app.account_service.repositories.CountryRepository;
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
    public List<CountryResponse> getAllCountries() {
        return countryMapper.toResponseList(countryRepository.findAllByOrderByNameAsc());
    }

    @Transactional(readOnly = true)
    public CountryResponse getCountryById(Long id) {
        return countryRepository.findById(id)
                .map(countryMapper::toResponse)
                .orElseThrow(CountryNotFoundException::new);
    }
}
