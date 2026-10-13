package com.app.diary_service.dtos.responses;

import com.app.diary_service.entities.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealResponse {
    private Long id;
    private MealType mealType;
    private LocalDateTime eatenAt;
    private LocalDateTime createdAt;
    private List<MealLogsInfoResponse> items;
}
