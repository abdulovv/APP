package com.app.diary_service.exceptions;

public class DiaryNotFoundException extends RuntimeException {
    public DiaryNotFoundException() {
        super("Diary not found");
    }
}
