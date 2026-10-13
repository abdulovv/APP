package com.app.auth_service.exceptions;

public class CountryNotAvailableException extends RuntimeException {
    public CountryNotAvailableException() {
        super("Registration is not available for this country");
    }
}
