package com.app.diary_service.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MealType {
    BREAKFAST("завтрак"),
    LUNCH("обед"),
    DINNER("ужин"),
    SNACK("перекус");

    private final String label;
}
