package com.app.account_service.exceptions;

public class TooManyAttemptsException extends RuntimeException {
    public TooManyAttemptsException(){super("Too many attempts");}
}
