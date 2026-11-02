package com.app.product_service.exceptions;

public class ProductCategoryNotFoundException extends RuntimeException {
    public ProductCategoryNotFoundException() {
        super("Product category not found");
    }
}
