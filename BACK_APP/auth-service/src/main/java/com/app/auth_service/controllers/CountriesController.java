package com.app.auth_service.controllers;

import com.app.auth_service.dto.mappers.CountryMapper;
import com.app.auth_service.dto.responses.CountryResponse;
import com.app.auth_service.entities.Country;
import com.app.auth_service.repositories.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/countries")
public class CountriesController {
    private final CountryRepository countryRepository;

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponse> getCountryById(@PathVariable Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Country not found"
                ));

        return ResponseEntity.ok(CountryMapper.fromEntityToResponse(country));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CountryResponse>> getCountries() {
        List<CountryResponse> countries = countryRepository.findAll()
                .stream()
                .map(CountryMapper::fromEntityToResponse)
                .toList();

        return ResponseEntity.ok(countries);
    }
}
