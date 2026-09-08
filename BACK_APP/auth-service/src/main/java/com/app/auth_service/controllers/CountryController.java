package com.app.auth_service.controllers;

import com.app.auth_service.dtos.common.CountryDTO;
import com.app.auth_service.mappers.CountryMapper;
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
public class CountryController {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable(name = "id") Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Country not found"
                ));

        return ResponseEntity.ok(
                countryMapper.toDto(country)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<CountryDTO>> getCountries() {
        List<CountryDTO> countries = countryRepository.findAll()
                .stream()
                .map(countryMapper::toDto)
                .toList();

        return ResponseEntity.ok(countries);
    }
}
