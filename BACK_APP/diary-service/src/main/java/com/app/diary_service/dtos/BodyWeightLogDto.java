package com.app.diary_service.dtos;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BodyWeightLogDto {
    private Long id;
    private Long diaryId;
    private BigDecimal weight;
    private LocalDateTime createdAt;
}
