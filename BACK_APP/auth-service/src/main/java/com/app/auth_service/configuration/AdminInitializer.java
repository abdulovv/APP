package com.app.auth_service.configuration;

import com.app.auth_service.entities.Country;
import com.app.auth_service.entities.Role;
import com.app.auth_service.entities.User;
import com.app.auth_service.repositories.CountryRepository;
import com.app.auth_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        try {
            if (!userRepository.existsByEmail("admino")) {
                User user = User.builder()
                        .email("admino")
                        .password(passwordEncoder.encode("123"))
                        .birthDate(LocalDate.now())
                        .firstname("admin")
                        .lastname("admin")
                        .phoneNumber("admino")
                        .country(countryRepository.findById(7L).get())
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create the first admin", e);
        }
    }
}