package com.app.product_service.exceptions;

public class ProductCategoryTypeMismatchException extends RuntimeException {
    public ProductCategoryTypeMismatchException(String message) {
        super(message);
    }
}
