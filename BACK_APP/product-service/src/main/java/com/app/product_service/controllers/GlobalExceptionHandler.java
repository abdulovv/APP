package com.app.product_service.controllers;

import com.app.product_service.dtos.ErrorResponse;
import com.app.product_service.exceptions.ProductCategoryNotFoundException;
import com.app.product_service.exceptions.InvalidProductNutrientsException;
import com.app.product_service.exceptions.NutrientNotFoundException;
import com.app.product_service.exceptions.ProductNotFoundException;
import com.app.product_service.exceptions.ProductCategoryTypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ProductNotFoundException.class,
            ProductCategoryNotFoundException.class,
            NutrientNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({InvalidProductNutrientsException.class, ProductCategoryTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Data conflict"));
    }
}
