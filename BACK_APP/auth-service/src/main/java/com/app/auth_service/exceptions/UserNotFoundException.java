package com.app.auth_service.exceptions;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(){
        super("User not found with email/phone");
    }
}
