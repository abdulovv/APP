package com.app.nutrition_service.exceptions;

public class NutritionNotFoundException extends RuntimeException {
    public NutritionNotFoundException() {
        super("Product not found");
    }
}
