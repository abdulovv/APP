package com.app.diary_service.exceptions;

public class MealLogNotFoundException extends RuntimeException {
    public MealLogNotFoundException() {
        super("Meal log not found");
    }
}
