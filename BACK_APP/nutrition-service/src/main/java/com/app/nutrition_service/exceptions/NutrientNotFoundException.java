package com.app.nutrition_service.exceptions;

public class NutrientNotFoundException extends RuntimeException {
    public NutrientNotFoundException() {
        super("Nutrient not found" );
    }
}
