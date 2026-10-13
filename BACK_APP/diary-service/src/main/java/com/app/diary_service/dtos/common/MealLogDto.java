package com.app.diary_service.dtos.common;

import com.app.diary_service.entities.MealType;

import java.time.LocalDateTime;

public record MealLogDto(
        Long id,
        Long diaryId,
        MealType mealType,
        LocalDateTime eatenAt,
        LocalDateTime createdAt
) {}
