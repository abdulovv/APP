package com.app.auth_service.exceptions;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException() {
        super("Page not found");
    }
}