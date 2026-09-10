package com.app.nutrition_service.exceptions;

public class WrongCategoryTypeException extends RuntimeException {
    public WrongCategoryTypeException(String message) {
        super(message);
    }
}
