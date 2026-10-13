package com.app.diary_service.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The names of the constants are stored in meal_logs.meal_type (@Enumerated(STRING)),
 * so they must match the CHECK constraint from V3 exactly.
 */
@Getter
@RequiredArgsConstructor
public enum MealType {
    BREAKFAST("завтрак"),
    LUNCH("обед"),
    DINNER("ужин"),
    SNACK("перекус");

    private final String label;
}
