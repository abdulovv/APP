package com.app.diary_service.exceptions;

public class DiaryAlreadyExistsException extends RuntimeException {
    public DiaryAlreadyExistsException() {
        super("Diary already exists");
    }
}
