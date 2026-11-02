package com.app.diary_service.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealLogItemResponse {
    private Long id;
    private Long productId;
    private BigDecimal weight;
}
