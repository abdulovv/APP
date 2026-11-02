package com.app.account_service.services;

import com.app.account_service.dtos.requests.SignInRequest;
import com.app.account_service.dtos.requests.SignUpRequest;
import com.app.account_service.dtos.responses.MessageResponse;
import com.app.account_service.entities.Country;
import com.app.account_service.entities.User;
import com.app.account_service.exceptions.CountryNotAvailableException;
import com.app.account_service.exceptions.CountryNotFoundException;
import com.app.account_service.exceptions.InvalidCredentialsException;
import com.app.account_service.exceptions.UserAlreadyExistsException;
import com.app.account_service.mappers.UserMapper;
import com.app.account_service.repositories.CountryRepository;
import com.app.account_service.repositories.UserRepository;
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
    public MessageResponse signUp(SignUpRequest request) {
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
        userRepository.save(userMapper.toNewUser(request, country, encodedPassword));

        return new MessageResponse("Success, you have signed up!");
    }

    @Transactional(readOnly = true)
    public User authenticate(SignInRequest request) {
        String login = request.login().trim();

        User user = userRepository.findByEmail(login.toLowerCase())
                .or(() -> userRepository.findByPhoneNumber(login))
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return user;
    }
}
