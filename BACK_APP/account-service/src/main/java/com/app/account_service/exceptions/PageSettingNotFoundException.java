package com.app.account_service.exceptions;

public class PageSettingNotFoundException extends RuntimeException {
    public PageSettingNotFoundException() {
        super("Page not found");
    }
}