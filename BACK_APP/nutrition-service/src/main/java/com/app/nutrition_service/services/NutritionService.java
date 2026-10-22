package com.app.nutrition_service.services;

import com.app.nutrition_service.dto.NutritionDTO;
import com.app.nutrition_service.entities.Nutrition;
import com.app.nutrition_service.exceptions.NutritionNotFoundException;
import com.app.nutrition_service.mappers.NutritionMapper;
import com.app.nutrition_service.repositories.NutritionRepository;
import com.app.nutrition_service.services.subservices.DrinkService;
import com.app.nutrition_service.services.subservices.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NutritionService {
    private final FoodService foodService;
    private final DrinkService drinkService;
    private final NutritionRepository nutritionRepository;
    private final NutritionMapper nutritionMapper;
    private final CategoryService categoryService;
    private final NutrientService nutrientService;

    // ==================== DRINK ====================
    public List<NutritionDTO> getAllDrinks() {
        return drinkService.getAllDrinks();
    }

    public List<NutritionDTO> getDrinksByCategoryName(String categoryName) {
        return drinkService.getDrinksByCategoryName(categoryName);
    }

    public NutritionDTO createDrink(NutritionDTO dto) {
        return drinkService.createDrink(dto);
    }

    // ==================== FOOD ====================
    public List<NutritionDTO> getAllFoods() {
        return foodService.getAllFoods();
    }

    public List<NutritionDTO> getFoodsByCategoryName(String categoryName) {
        return foodService.getFoodsByCategoryName(categoryName);
    }

    public NutritionDTO createFood(NutritionDTO dto) {
        return foodService.createFood(dto);
    }

    // ==================== GENERAL ====================
    @Transactional(readOnly = true)
    public List<NutritionDTO> getAllNutrition() {
        return nutritionMapper.toDtoList(nutritionRepository.findAll());
    }

    @Transactional(readOnly = true)
    public NutritionDTO getNutritionById(Long id) {
        return nutritionMapper.toDto(findNutrition(id));
    }

    @Transactional(readOnly = true)
    public List<NutritionDTO> searchNutrition(String query) {
        return nutritionMapper.toDtoList(
                nutritionRepository.findByNameContainingIgnoreCaseOrderByNameAsc(query.trim())
        );
    }

    @Transactional
    public NutritionDTO updateNutrition(Long id, NutritionDTO nutritionDTO) {
        Nutrition nutrition = findNutrition(id);
        nutritionMapper.updateEntity(nutritionDTO, nutrition);

        if (nutritionDTO.getCategory() != null) {
            nutrition.setCategory(categoryService.getCategory(nutritionDTO.getCategory()));
        }

        if (nutritionDTO.getNutrients() != null && !nutritionDTO.getNutrients().isEmpty()) {
            nutrientService.replaceNutrients(nutrition, nutritionDTO.getNutrients());
        }

        return nutritionMapper.toDto(nutritionRepository.saveAndFlush(nutrition));
    }

    @Transactional
    public void deleteNutrition(Long id) {
        if (!nutritionRepository.existsById(id)) {
            throw new NutritionNotFoundException();
        }
        nutritionRepository.deleteById(id);
    }

    // Мягкое удаление: продукт скрывается, но записи в diary-service на него не ломаются
    @Transactional
    public void deactivateNutrition(Long id) {
        Nutrition nutrition = findNutrition(id);
        nutrition.setIsActive(false);
    }

    private Nutrition findNutrition(Long id) {
        return nutritionRepository.findById(id)
                .orElseThrow(NutritionNotFoundException::new);
    }
}
