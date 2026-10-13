package com.app.diary_service.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class DiaryResponse{
    private Long id;
    private Long userId;
    private BigDecimal height;
    private LocalDate birthDate;
    private List<MealResponse> meals;
}
