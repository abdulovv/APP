package com.app.nutrition_service.exceptions;

public class NoNutrientsException extends RuntimeException {
    public NoNutrientsException(String message) {
        super(message);
    }
}
