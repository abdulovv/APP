package com.app.auth_service.dtos.requests;

final class NameFormatter {

    private NameFormatter() {
    }

    static String capitalize(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }
        String trimmed = value.trim();
        return trimmed.substring(0, 1).toUpperCase() + trimmed.substring(1).toLowerCase();
    }
}
