package com.app.nutrition_service.exceptions;

public class DrinksNotFoundException extends Throwable {
    public DrinksNotFoundException() {
        super("Drinks not found");
    }
}
