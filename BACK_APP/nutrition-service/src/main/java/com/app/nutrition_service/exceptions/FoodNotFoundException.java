package com.app.nutrition_service.exceptions;

public class FoodNotFoundException extends Throwable {
    public FoodNotFoundException(){
        super("Food not found");
    };
}
