package com.app.account_service.config;

import com.app.account_service.entities.Country;
import com.app.account_service.entities.User;
import com.app.account_service.entities.UserRole;
import com.app.account_service.repositories.CountryRepository;
import com.app.account_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DefaultAdminInitializer implements ApplicationRunner {
    private static final String ADMIN_LOGIN = "admino";
    private static final String ADMIN_PASSWORD = "123";
    private static final String ADMIN_COUNTRY = "Belarus";

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.existsByEmail(ADMIN_LOGIN)) {
            return;
        }
        Country country = countryRepository.findByName(ADMIN_COUNTRY)
                .orElseThrow(() -> new IllegalStateException("Country " + ADMIN_COUNTRY + " is missing in the database"));

        userRepository.save(User.builder()
                .email(ADMIN_LOGIN)
                .phoneNumber(ADMIN_LOGIN)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .firstname("admin")
                .lastname("admin")
                .birthDate(LocalDate.now())
                .country(country)
                .role(UserRole.ADMIN)
                .build());
    }
}
