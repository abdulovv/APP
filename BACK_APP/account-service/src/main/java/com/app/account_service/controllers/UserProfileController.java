package com.app.account_service.controllers;

import com.app.account_service.dtos.requests.UpdateEmailRequest;
import com.app.account_service.dtos.requests.UpdateNameRequest;
import com.app.account_service.dtos.requests.UpdatePasswordRequest;
import com.app.account_service.dtos.requests.UpdatePhoneRequest;
import com.app.account_service.dtos.responses.MessageResponse;
import com.app.account_service.dtos.responses.UserProfileResponse;
import com.app.account_service.services.UserProfileService;
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
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(@RequestHeader(GatewayHeaders.USER_ID) Long userId) {
        return ResponseEntity.ok(userProfileService.getProfile(userId));
    }

    @PatchMapping("/name")
    public ResponseEntity<MessageResponse> updateName(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        userProfileService.updateName(userId, request);
        return ResponseEntity.ok(new MessageResponse("Name updated"));
    }

    @PatchMapping("/email")
    public ResponseEntity<MessageResponse> updateEmail(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @Valid @RequestBody UpdateEmailRequest request
    ) {
        userProfileService.updateEmail(userId, request);
        return ResponseEntity.ok(new MessageResponse("Email updated"));
    }

    @PatchMapping("/phone")
    public ResponseEntity<MessageResponse> updatePhone(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @Valid @RequestBody UpdatePhoneRequest request
    ) {
        userProfileService.updatePhone(userId, request);
        return ResponseEntity.ok(new MessageResponse("Phone updated"));
    }

    @PatchMapping("/password")
    public ResponseEntity<MessageResponse> updatePassword(
            @RequestHeader(GatewayHeaders.USER_ID) Long userId,
            @Valid @RequestBody UpdatePasswordRequest request
    ) {
        userProfileService.updatePassword(userId, request);
        return ResponseEntity.ok(new MessageResponse("Password updated"));
    }
}
