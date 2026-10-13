package com.app.diary_service.dtos.requests;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Past;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DiaryRequest(
        @DecimalMin("50.0")
        @DecimalMax("300.0")
        BigDecimal height,

        @Past
        LocalDate birthDate
) {
}
