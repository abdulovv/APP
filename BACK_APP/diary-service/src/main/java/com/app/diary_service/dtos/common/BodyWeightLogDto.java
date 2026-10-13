package com.app.diary_service.dtos.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BodyWeightLogDto(
        Long id,
        Long diaryId,
        BigDecimal weight,
        LocalDateTime createdAt
) {}
