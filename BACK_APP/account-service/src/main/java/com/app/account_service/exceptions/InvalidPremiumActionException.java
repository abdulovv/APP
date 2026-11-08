package com.app.account_service.exceptions;

public class InvalidPremiumActionException extends RuntimeException {
    public InvalidPremiumActionException(String message) {
        super(message);
    }
}
