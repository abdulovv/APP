package com.app.auth_service.dtos.requests;

public record SignInRequest(
        String uniqueField,
        String password
) {
}
