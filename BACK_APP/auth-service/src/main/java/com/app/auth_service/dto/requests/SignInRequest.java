package com.app.auth_service.dto.requests;

public record SignInRequest(
        String uniqueField,
        String password
) {
}
