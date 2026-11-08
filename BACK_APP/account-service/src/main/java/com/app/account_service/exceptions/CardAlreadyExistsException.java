package com.app.account_service.exceptions;

public class CardAlreadyExistsException extends RuntimeException {
    public CardAlreadyExistsException() {
        super("This card is already added");
    }
}
