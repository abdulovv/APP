package com.app.diary_service.dtos.requests;

import com.app.diary_service.entities.MealType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record MealLogRequest(
        @NotNull
        MealType mealType,

        LocalDateTime eatenAt,

        @NotEmpty
        List<@Valid MealItemRequest> items
) {
}
