package com.app.product_service.utils;

import com.app.product_service.dtos.ProductNutrientDto;
import com.app.product_service.exceptions.InvalidProductNutrientsException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NutrientUtils {
    private NutrientUtils() {
    }

    /** У продукта должен быть хотя бы один нутриент, и у каждого заданы id и значение. */
    public static void validate(List<ProductNutrientDto> nutrients) {
        if (nutrients == null || nutrients.isEmpty()) {
            throw new InvalidProductNutrientsException("Product must contain at least one nutrient");
        }
        for (ProductNutrientDto nutrientValue : nutrients) {
            if (nutrientValue.getNutrientId() == null || nutrientValue.getValue() == null) {
                throw new InvalidProductNutrientsException("Nutrient id and value are required");
            }
        }
    }

    /** Изменяемая карта «id нутриента → значение». */
    public static Map<Long, BigDecimal> valuesByNutrientId(List<ProductNutrientDto> nutrients) {
        Map<Long, BigDecimal> values = new HashMap<>();
        for (ProductNutrientDto nutrientValue : nutrients) {
            values.put(nutrientValue.getNutrientId(), nutrientValue.getValue());
        }
        return values;
    }
}
