package com.app.diary_service.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DiaryResponse(
        Long id,
        Long userId,
        BigDecimal height,
        LocalDate birthDate
) {}
