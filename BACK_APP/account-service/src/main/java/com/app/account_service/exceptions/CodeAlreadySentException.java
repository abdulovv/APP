package com.app.account_service.exceptions;

public class CodeAlreadySentException extends RuntimeException {
    public CodeAlreadySentException(){super("The code has already been sent");};
}
