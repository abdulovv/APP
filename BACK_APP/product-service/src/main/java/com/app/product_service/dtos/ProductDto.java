package com.app.product_service.dtos;

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
public class ProductDto {
    private Long id;
    private String name;
    private String portionName;
    private BigDecimal portionWeight;
    private ProductCategoryDto category;
    private Boolean isActive;

    @Builder.Default
    private List<ProductNutrientDto> nutrients = new ArrayList<>();
}
