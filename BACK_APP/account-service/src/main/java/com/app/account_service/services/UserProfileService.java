package com.app.account_service.services;

import com.app.account_service.dtos.requests.UpdateEmailRequest;
import com.app.account_service.dtos.requests.UpdateNameRequest;
import com.app.account_service.dtos.requests.UpdatePasswordRequest;
import com.app.account_service.dtos.requests.UpdatePhoneRequest;
import com.app.account_service.dtos.responses.UserProfileResponse;
import com.app.account_service.entities.User;
import com.app.account_service.exceptions.IncorrectCurrentPasswordException;
import com.app.account_service.exceptions.InvalidCodeException;
import com.app.account_service.exceptions.UserAlreadyExistsException;
import com.app.account_service.exceptions.UserNotFoundException;
import com.app.account_service.mappers.UserMapper;
import com.app.account_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserRepository userRepository;
    private final PremiumService premiumService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(Long userId) {
        return userMapper.toProfileResponse(
                findUser(userId),
                premiumService.getPremium(userId),
                premiumService.isPremiumActive(userId));
    }

    @Transactional
    public void updateName(Long userId, UpdateNameRequest request) {
        User user = findUser(userId);
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
    }

    @Transactional
    public void updateEmail(Long userId, UpdateEmailRequest request) {
        if (userRepository.existsByEmailAndIdNot(request.email(), userId)) {
            throw new UserAlreadyExistsException("Email is already registered");
        }
        findUser(userId).setEmail(request.email());
    }

    @Transactional
    public void updatePhone(Long userId, UpdatePhoneRequest request) {
        if (userRepository.existsByPhoneNumberAndIdNot(request.phoneNumber(), userId)) {
            throw new UserAlreadyExistsException("Phone number is already registered");
        }
        findUser(userId).setPhoneNumber(request.phoneNumber());
    }

    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequest request) {
        User user = findUser(userId);
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IncorrectCurrentPasswordException();
        }
        if (!verificationCodeService.check(passwordCodeKey(userId), request.code())) {
            throw new InvalidCodeException();
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));

        emailService.sendPasswordChanged(user.getEmail(), user.getFirstname());
    }

    public void sendPasswordCode(Long userId) {
        User user = findUser(userId);
        String code = verificationCodeService.generate(passwordCodeKey(userId));
        emailService.sendCode(user.getEmail(), "Confirm password change", code, VerificationCodeService.Duration());
    }

    private String passwordCodeKey(Long userId) {
        return "password:" + userId;
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
