package com.app.auth_service.services;

import com.app.auth_service.dtos.requests.UpdateEmailRequest;
import com.app.auth_service.dtos.requests.UpdateNameRequest;
import com.app.auth_service.dtos.requests.UpdatePasswordRequest;
import com.app.auth_service.dtos.requests.UpdatePhoneRequest;
import com.app.auth_service.dtos.responses.UserResponse;
import com.app.auth_service.entities.User;
import com.app.auth_service.exceptions.IncorrectPasswordException;
import com.app.auth_service.exceptions.UserAlreadyExistsException;
import com.app.auth_service.exceptions.UserNotFoundException;
import com.app.auth_service.mappers.UserMapper;
import com.app.auth_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        return userMapper.toResponse(findUser(id));
    }

    @Transactional
    public void updateName(Long id, UpdateNameRequest request) {
        User user = findUser(id);
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
    }

    @Transactional
    public void updateEmail(Long id, UpdateEmailRequest request) {
        if (userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new UserAlreadyExistsException("Email is already registered");
        }
        findUser(id).setEmail(request.email());
    }

    @Transactional
    public void updatePhone(Long id, UpdatePhoneRequest request) {
        if (userRepository.existsByPhoneNumberAndIdNot(request.phoneNumber(), id)) {
            throw new UserAlreadyExistsException("Phone number is already registered");
        }
        findUser(id).setPhoneNumber(request.phoneNumber());
    }

    @Transactional
    public void updatePassword(Long id, UpdatePasswordRequest request) {
        User user = findUser(id);
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
