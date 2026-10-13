package com.app.diary_service.dtos.common;

import java.math.BigDecimal;

public record MealLogsInfoDto(
        Long id,
        Long mealLogId,
        Long nutritionId,
        BigDecimal weight
) {}
