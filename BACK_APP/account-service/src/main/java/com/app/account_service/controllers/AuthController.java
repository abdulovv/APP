package com.app.account_service.controllers;

import com.app.account_service.dtos.requests.SendCodeRequest;
import com.app.account_service.dtos.requests.SignInRequest;
import com.app.account_service.dtos.requests.SignUpRequest;
import com.app.account_service.dtos.responses.MessageResponse;
import com.app.account_service.entities.UserRole;
import com.app.account_service.entities.User;
import com.app.account_service.services.AuthService;
import com.app.account_service.services.VerificationCodeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<MessageResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(signUpRequest));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<MessageResponse> signIn(
            @Valid @RequestBody SignInRequest request,
            HttpServletRequest httpServletRequest
    ) {
        User user = authService.authenticate(request);
        Long id = user.getId();
        UserRole role = user.getRole();
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) session.invalidate();
        session = httpServletRequest.getSession(true);
        session.setAttribute("userId", id);
        session.setAttribute("role", role.name());
        return ResponseEntity.ok(new MessageResponse("Welcome back!"));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<Void> signOut(HttpServletRequest http) {
        HttpSession session = http.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/sign-up/send-code")
    public void sendCode(
            @Valid @RequestBody SendCodeRequest request
    ){
        authService.sendCode(request.email());
    }
}
