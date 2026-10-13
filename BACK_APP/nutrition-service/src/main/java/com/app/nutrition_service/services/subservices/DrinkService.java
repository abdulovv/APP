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
public class DrinkService {
    private final NutritionRepository nutritionRepository;
    private final CategoryService categoryService;
    private final NutrientService nutrientService;
    private final NutritionMapper nutritionMapper;

    @Transactional(readOnly = true)
    public List<NutritionDTO> getAllDrinks() {
        return nutritionMapper.toDtoList(nutritionRepository.findByCategoryType(CategoryType.DRINK));
    }

    @Transactional(readOnly = true)
    public List<NutritionDTO> getDrinksByCategoryName(String categoryName) {
        return nutritionMapper.toDtoList(
                nutritionRepository.findByCategoryNameAndCategoryType(categoryName, CategoryType.DRINK)
        );
    }

    @Transactional
    public NutritionDTO createDrink(NutritionDTO dto) {
        Nutrition drink = nutritionMapper.toEntity(dto);
        drink.setCategory(categoryService.checkCategory(dto.getCategory(), CategoryType.DRINK));
        nutrientService.addNutrients(drink, dto.getNutrients());

        return nutritionMapper.toDto(nutritionRepository.save(drink));
    }
}
