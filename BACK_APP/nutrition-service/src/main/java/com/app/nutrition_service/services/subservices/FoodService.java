package com.app.nutrition_service.services.subservices;

import com.app.nutrition_service.dto.NutritionDTO;
import com.app.nutrition_service.entities.CategoryType;
import com.app.nutrition_service.entities.Nutrition;
import com.app.nutrition_service.mappers.NutritionMapper;
import com.app.nutrition_service.repositories.NutritionRepository;
import com.app.nutrition_service.services.CategoryService;
import com.app.nutrition_service.services.NutrientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final NutritionRepository nutritionRepository;
    private final CategoryService categoryService;
    private final NutrientService nutrientService;
    private final NutritionMapper nutritionMapper;

    @Transactional(readOnly = true)
    public List<NutritionDTO> getAllFoods() {
        return nutritionMapper.toDtoList(nutritionRepository.findByCategoryType(CategoryType.FOOD));
    }

    @Transactional(readOnly = true)
    public List<NutritionDTO> getFoodsByCategoryName(String categoryName) {
        return nutritionMapper.toDtoList(
                nutritionRepository.findByCategoryNameAndCategoryType(categoryName, CategoryType.FOOD)
        );
    }

    @Transactional
    public NutritionDTO createFood(NutritionDTO dto) {
        Nutrition food = nutritionMapper.toEntity(dto);
        food.setCategory(categoryService.checkCategory(dto.getCategory(), CategoryType.FOOD));
        nutrientService.addNutrients(food, dto.getNutrients());

        return nutritionMapper.toDto(nutritionRepository.save(food));
    }
}
