package com.app.auth_service.services;

import com.app.auth_service.dto.mappers.UserMapper;
import com.app.auth_service.dto.requests.SignInRequest;
import com.app.auth_service.dto.requests.SignUpRequest;
import com.app.auth_service.dto.responses.AuthResponse;
import com.app.auth_service.entities.Country;
import com.app.auth_service.entities.User;
import com.app.auth_service.exceptions.IncorrectPasswordException;
import com.app.auth_service.exceptions.UserNotFoundException;
import com.app.auth_service.repositories.CountryRepository;
import com.app.auth_service.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse signUp(SignUpRequest request){
        Long countryId = request.countryId();
        Optional<Country> countryOpt = countryRepository.findById(countryId);

        if (countryOpt.isPresent()){
            User user = UserMapper.mapRequestToEntity(countryOpt.get(), request, passwordEncoder);
            user = userRepository.save(user);
            return UserMapper.mapUserToResponse(user);
        }

        return new AuthResponse("invalid country");
    }

    public ResponseEntity<AuthResponse> signIn(SignInRequest request) throws UserNotFoundException, IncorrectPasswordException {
        String emailOrNumber = request.uniqueField();
        String rawPassword = request.password();
        Optional<User> userOpt = findUserByUniqueField(emailOrNumber);

        User user = userOpt.orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(rawPassword, user.getPassword())){
            throw new IncorrectPasswordException();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse("Success, you have signed in"));
    }

    private Optional<User> findUserByUniqueField(String uniqueField){
        Optional<User> userOptional = userRepository.findByEmail(uniqueField);
        if (userOptional.isEmpty()){
            userOptional = userRepository.findByPhoneNumber(uniqueField);
        }

        return userOptional;
    }
}
