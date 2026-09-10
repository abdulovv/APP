package com.app.nutrition_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutritionDTO {
    private Long id;
    private String name;
    private String portionName;
    private BigDecimal portionWeight;
    private CategoryDTO category;
    private List<NutrientValueDTO> nutrients = new ArrayList<>();
}
