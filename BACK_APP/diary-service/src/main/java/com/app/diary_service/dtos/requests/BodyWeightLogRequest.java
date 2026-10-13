package com.app.diary_service.dtos.requests;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BodyWeightLogRequest(
        @NotNull
        @DecimalMin("20.0")
        @DecimalMax("500.0")
        BigDecimal weight
) {
}
