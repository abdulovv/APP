package com.app.auth_service.controllers;

import com.app.auth_service.dtos.requests.SignInRequest;
import com.app.auth_service.dtos.requests.SignUpRequest;
import com.app.auth_service.dtos.responses.AuthResponse;
import com.app.auth_service.exceptions.IncorrectPasswordException;
import com.app.auth_service.exceptions.UserNotFoundException;
import com.app.auth_service.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody SignUpRequest request){
        AuthResponse response = authService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody SignInRequest request) throws UserNotFoundException, IncorrectPasswordException {
        return authService.signIn(request);
    }
}
