package com.app.auth_service.exceptions;

public class IncorrectPasswordException extends Throwable {
    public IncorrectPasswordException(){
        super("Incorrect password");
    }
}
