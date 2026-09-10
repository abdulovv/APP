package com.app.nutrition_service.services.subservices;

import com.app.nutrition_service.dto.NutrientValueDTO;
import com.app.nutrition_service.dto.NutritionDTO;
import com.app.nutrition_service.entities.*;
import com.app.nutrition_service.exceptions.FoodNotFoundException;
import com.app.nutrition_service.exceptions.NoNutrientsException;
import com.app.nutrition_service.exceptions.NutrientNotFoundException;
import com.app.nutrition_service.mappers.NutritionMapper;
import com.app.nutrition_service.repositories.NutrientRepository;
import com.app.nutrition_service.repositories.NutritionRepository;
import com.app.nutrition_service.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FoodService {
    private final NutritionRepository nutritionRepository;
    private final NutrientRepository nutrientRepository;
    private final CategoryService categoryService;
    private final NutritionMapper nutritionMapper;

    public List<NutritionDTO> getAllFoods() {
        List<Nutrition> foods = nutritionRepository.findByCategoryType(CategoryType.FOOD);
        return nutritionMapper.toDtoList(foods);
    }

    public List<NutritionDTO> getFoodsByCategoryName(String categoryName) throws FoodNotFoundException {
        List<Nutrition> foods = nutritionRepository
                .findByCategoryNameAndCategoryType(categoryName, CategoryType.FOOD);

        if (foods.isEmpty()) {
            throw new FoodNotFoundException();
        }

        return nutritionMapper.toDtoList(foods);
    }

    @Transactional(rollbackFor = NoNutrientsException.class)
    public NutritionDTO createFood(NutritionDTO dto) throws NoNutrientsException {
        Nutrition food = nutritionMapper.toEntity(dto);
        Category category = categoryService.checkCategory(dto.getCategory(), CategoryType.FOOD);
        food.setCategory(category);

        nutritionRepository.save(food);
        addNutrients(food, dto.getNutrients());

        return nutritionMapper.toDto(food);
    }

    private void addNutrients(Nutrition nutrition, List<NutrientValueDTO> nutrients) throws NoNutrientsException {
        if (nutrients == null || nutrients.isEmpty()) {
            throw new NoNutrientsException("Creation nutrition without nutrients");
        }

        for (NutrientValueDTO n : nutrients) {
            Nutrient nutrient = nutrientRepository.findById(n.getNutrientId())
                    .orElseThrow(NutrientNotFoundException::new);

            NutritionNutrient link = NutritionNutrient.builder()
                    .nutrition(nutrition)
                    .nutrient(nutrient)
                    .value(n.getValue())
                    .build();

            nutrition.getNutrients().add(link);
        }

        nutritionRepository.save(nutrition);
    }
}
