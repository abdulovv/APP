package com.app.diary_service.dtos.common;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DiaryDto(
        Long id,
        Long userId,
        BigDecimal height,
        LocalDate birthDate
) {}
