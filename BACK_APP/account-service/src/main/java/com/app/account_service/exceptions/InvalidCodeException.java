package com.app.account_service.exceptions;

public class InvalidCodeException extends RuntimeException {
    public InvalidCodeException(){super("Invalid or expired code");}
}
