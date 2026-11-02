package com.app.product_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductNutrientDto {
    private Long nutrientId;
    private String name;
    private String unit;
    private BigDecimal value;
}
