package com.app.account_service.exceptions;

public class IncorrectCurrentPasswordException extends RuntimeException {
    public IncorrectCurrentPasswordException() {
        super("Current password is incorrect");
    }
}
