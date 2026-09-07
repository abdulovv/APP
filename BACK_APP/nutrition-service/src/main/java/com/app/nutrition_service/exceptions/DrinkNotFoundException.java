package com.app.nutrition_service.exceptions;

public class DrinkNotFoundException extends Throwable {
    public DrinkNotFoundException(){
        super("Drink not found");
    }
}
