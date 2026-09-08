package com.app.auth_service.controllers;

import com.app.auth_service.dtos.responses.AuthResponse;
import com.app.auth_service.exceptions.IncorrectPasswordException;
import com.app.auth_service.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<AuthResponse> handleUserNotFoundException() {
        AuthResponse response = new AuthResponse("incorrect phone/email");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<AuthResponse> handleIncorrectPasswordException() {
        AuthResponse response = new AuthResponse("incorrect password");
        return ResponseEntity.badRequest().body(response);
    }
}
