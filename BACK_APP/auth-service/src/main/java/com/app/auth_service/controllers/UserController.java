package com.app.auth_service.controllers;

import com.app.auth_service.dtos.requests.UpdateEmailRequest;
import com.app.auth_service.dtos.requests.UpdateNameRequest;
import com.app.auth_service.dtos.requests.UpdatePasswordRequest;
import com.app.auth_service.dtos.requests.UpdatePhoneRequest;
import com.app.auth_service.dtos.responses.AuthResponse;
import com.app.auth_service.dtos.responses.UserResponse;
import com.app.auth_service.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me")
public class UserController {
    private static final String USER_ID_HEADER = "X-User-Id";
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getCurrentUser(@RequestHeader(value = USER_ID_HEADER) Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PatchMapping("/name")
    public ResponseEntity<AuthResponse> updateName(
            @RequestHeader(value = USER_ID_HEADER) Long userId,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        userService.updateName(userId, request);
        return ResponseEntity.ok(new AuthResponse("Name updated"));
    }

    @PatchMapping("/email")
    public ResponseEntity<AuthResponse> updateEmail(
            @RequestHeader(value = USER_ID_HEADER) Long userId,
            @Valid @RequestBody UpdateEmailRequest request
    ) {
        userService.updateEmail(userId, request);
        return ResponseEntity.ok(new AuthResponse("Email updated"));
    }

    @PatchMapping("/phone")
    public ResponseEntity<AuthResponse> updatePhone(
            @RequestHeader(value = USER_ID_HEADER) Long userId,
            @Valid @RequestBody UpdatePhoneRequest request
    ) {
        userService.updatePhone(userId, request);
        return ResponseEntity.ok(new AuthResponse("Phone updated"));
    }

    @PatchMapping("/password")
    public ResponseEntity<AuthResponse> updatePassword(
            @RequestHeader(value = USER_ID_HEADER) Long userId,
            @Valid @RequestBody UpdatePasswordRequest request
    ) {
        userService.updatePassword(userId, request);
        return ResponseEntity.ok(new AuthResponse("Password updated"));
    }
}
