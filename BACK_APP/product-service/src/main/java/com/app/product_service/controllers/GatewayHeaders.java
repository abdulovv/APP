package com.app.product_service.controllers;

public final class GatewayHeaders {
    public static final String USER_ID = "X-User-Id";
    public static final String USER_ROLE = "X-User-Role";

    private static final String ADMIN_ROLE = "ADMIN";

    private GatewayHeaders() {
    }

    public static boolean isAdmin(String role) {
        return ADMIN_ROLE.equals(role);
    }
}
