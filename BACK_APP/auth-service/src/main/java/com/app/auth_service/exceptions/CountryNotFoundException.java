package com.app.auth_service.exceptions;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException() {
        super("Country not found");
    }
}
