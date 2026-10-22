package com.app.nutrition_service.services;

import com.app.nutrition_service.dto.NutrientDTO;
import com.app.nutrition_service.dto.NutrientValueDTO;
import com.app.nutrition_service.entities.Nutrient;
import com.app.nutrition_service.entities.Nutrition;
import com.app.nutrition_service.entities.NutritionNutrient;
import com.app.nutrition_service.exceptions.NoNutrientsException;
import com.app.nutrition_service.exceptions.NutrientNotFoundException;
import com.app.nutrition_service.mappers.NutrientMapper;
import com.app.nutrition_service.repositories.NutrientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NutrientService {
    private final NutrientRepository nutrientRepository;
    private final NutrientMapper nutrientMapper;

    @Transactional(readOnly = true)
    public List<NutrientDTO> getAll() {
        return nutrientMapper.toDtoList(nutrientRepository.findAll());
    }

    public void addNutrients(Nutrition nutrition, List<NutrientValueDTO> nutrients) {
        if (nutrients == null || nutrients.isEmpty()) {
            throw new NoNutrientsException("Nutrition must contain at least one nutrient");
        }

        for (NutrientValueDTO value : nutrients) {
            if (value.getNutrientId() == null || value.getValue() == null) {
                throw new NoNutrientsException("Nutrient id and value are required");
            }

            Nutrient nutrient = nutrientRepository.findById(value.getNutrientId())
                    .orElseThrow(NutrientNotFoundException::new);

            NutritionNutrient link = NutritionNutrient.builder()
                    .nutrition(nutrition)
                    .nutrient(nutrient)
                    .value(value.getValue())
                    .build();

            nutrition.getNutrients().add(link);
        }
    }

    /**
     * Обновляет нутриенты продукта без пересоздания связей:
     * существующие — меняем value, отсутствующие в запросе — удаляем, новые — добавляем.
     * Так не ловим UNIQUE (nutrition_id, nutrient_id): Hibernate делает INSERT раньше DELETE.
     */
    public void replaceNutrients(Nutrition nutrition, List<NutrientValueDTO> nutrients) {
        if (nutrients == null || nutrients.isEmpty()) {
            throw new NoNutrientsException("Nutrition must contain at least one nutrient");
        }

        Map<Long, BigDecimal> newValues = new HashMap<>();
        for (NutrientValueDTO value : nutrients) {
            if (value.getNutrientId() == null || value.getValue() == null) {
                throw new NoNutrientsException("Nutrient id and value are required");
            }
            newValues.put(value.getNutrientId(), value.getValue());
        }

        Iterator<NutritionNutrient> iterator = nutrition.getNutrients().iterator();
        while (iterator.hasNext()) {
            NutritionNutrient link = iterator.next();
            BigDecimal newValue = newValues.remove(link.getNutrient().getId());
            if (newValue == null) {
                iterator.remove();
            } else {
                link.setValue(newValue);
            }
        }

        newValues.forEach((nutrientId, value) -> {
            Nutrient nutrient = nutrientRepository.findById(nutrientId)
                    .orElseThrow(NutrientNotFoundException::new);

            nutrition.getNutrients().add(NutritionNutrient.builder()
                    .nutrition(nutrition)
                    .nutrient(nutrient)
                    .value(value)
                    .build());
        });
    }
}
