package com.app.auth_service.services;

import com.app.auth_service.dtos.requests.SignInRequest;
import com.app.auth_service.dtos.requests.SignUpRequest;
import com.app.auth_service.dtos.responses.AuthResponse;
import com.app.auth_service.entities.Country;
import com.app.auth_service.entities.User;
import com.app.auth_service.exceptions.CountryNotAvailableException;
import com.app.auth_service.exceptions.CountryNotFoundException;
import com.app.auth_service.exceptions.InvalidCredentialsException;
import com.app.auth_service.exceptions.UserAlreadyExistsException;
import com.app.auth_service.mappers.UserMapper;
import com.app.auth_service.repositories.CountryRepository;
import com.app.auth_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Email is already registered");
        }
        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new UserAlreadyExistsException("Phone number is already registered");
        }

        Country country = countryRepository.findById(request.countryId())
                .orElseThrow(CountryNotFoundException::new);
        if (!Boolean.TRUE.equals(country.getAvailability())) {
            throw new CountryNotAvailableException();
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        userRepository.save(userMapper.toEntity(request, country, encodedPassword));

        return new AuthResponse("Success, you have signed up!");
    }

    @Transactional(readOnly = true)
    public User authenticate(SignInRequest request) {
        String login = request.uniqueField().trim();

        User user = userRepository.findByEmail(login.toLowerCase())
                .or(() -> userRepository.findByPhoneNumber(login))
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return user;
    }
}
