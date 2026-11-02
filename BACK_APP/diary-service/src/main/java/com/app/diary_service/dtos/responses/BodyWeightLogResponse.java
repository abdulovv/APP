package com.app.diary_service.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BodyWeightLogResponse(
        Long id,
        Long diaryId,
        BigDecimal weight,
        LocalDateTime createdAt
) {}
