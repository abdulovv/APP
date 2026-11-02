package com.app.account_service.controllers;

import com.app.account_service.entities.UserRole;

public final class GatewayHeaders {
    public static final String USER_ID = "X-User-Id";
    public static final String USER_ROLE = "X-User-Role";

    private GatewayHeaders() {
    }

    public static boolean isAdmin(String role) {
        return UserRole.ADMIN.name().equals(role);
    }
}
