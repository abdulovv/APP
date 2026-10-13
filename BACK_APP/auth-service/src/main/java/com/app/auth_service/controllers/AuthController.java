package com.app.auth_service.controllers;

import com.app.auth_service.dtos.requests.SignInRequest;
import com.app.auth_service.dtos.requests.SignUpRequest;
import com.app.auth_service.dtos.responses.AuthResponse;
import com.app.auth_service.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(signUpRequest));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(
            @Valid @RequestBody SignInRequest request,
            HttpServletRequest httpServletRequest
    ) {
        Long id = authService.authenticate(request).getId();
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) session.invalidate();
        session = httpServletRequest.getSession(true);
        session.setAttribute("userId", id);
        return ResponseEntity.ok(new AuthResponse("Welcome back!"));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<Void> signOut(HttpServletRequest http) {
        HttpSession session = http.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.status(204).build();
    }
}
