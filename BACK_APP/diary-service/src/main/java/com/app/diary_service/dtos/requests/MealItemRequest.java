package com.app.diary_service.dtos.requests;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MealItemRequest(
        @NotNull
        Long nutritionId,

        @NotNull
        @DecimalMin("0.01")
        @DecimalMax("10000.00")
        BigDecimal weight
) {
}
