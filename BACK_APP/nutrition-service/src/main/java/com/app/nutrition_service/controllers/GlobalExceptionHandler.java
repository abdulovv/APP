package com.app.nutrition_service.controllers;

import com.app.nutrition_service.dto.ErrorResponse;
import com.app.nutrition_service.exceptions.CategoryNotFoundException;
import com.app.nutrition_service.exceptions.NoNutrientsException;
import com.app.nutrition_service.exceptions.NutrientNotFoundException;
import com.app.nutrition_service.exceptions.NutritionNotFoundException;
import com.app.nutrition_service.exceptions.WrongCategoryTypeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            NutritionNotFoundException.class,
            CategoryNotFoundException.class,
            NutrientNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({NoNutrientsException.class, WrongCategoryTypeException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Data conflict"));
    }
}
