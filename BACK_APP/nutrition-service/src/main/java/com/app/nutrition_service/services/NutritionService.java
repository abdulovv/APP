package com.app.nutrition_service.services;

import com.app.nutrition_service.dto.NutritionDTO;
import com.app.nutrition_service.entities.Nutrition;
import com.app.nutrition_service.exceptions.*;
import com.app.nutrition_service.mappers.NutritionMapper;
import com.app.nutrition_service.repositories.NutritionRepository;
import com.app.nutrition_service.services.subservices.DrinkService;
import com.app.nutrition_service.services.subservices.FoodService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class NutritionService {
    private final FoodService foodService;
    private final DrinkService drinkService;
    private final CategoryService categoryService;
    private final NutritionRepository nutritionRepository;
    private final NutritionMapper nutritionMapper;

    // ==================== DRINK ====================
    public ResponseEntity<List<NutritionDTO>> getAllDrinks() throws DrinksNotFoundException {
        return drinkService.getAllDrinks();
    }

    public List<NutritionDTO> getDrinksByCategoryName(String categoryName) throws DrinksNotFoundException {
        return drinkService.getDrinksByCategoryName(categoryName);
    }

    public NutritionDTO createDrink(NutritionDTO dto) throws NoNutrientsException {
        return drinkService.createDrink(dto);
    }

    // ==================== FOOD ====================
    public List<NutritionDTO> getAllFoods() {
        return foodService.getAllFoods();
    }

    public List<NutritionDTO> getFoodByCategoryName(String categoryName) throws FoodNotFoundException {
        return foodService.getFoodsByCategoryName(categoryName);
    }

    public NutritionDTO createFood(NutritionDTO dto) throws NoNutrientsException {
        return foodService.createFood(dto);
    }

    // ==================== GENERAL ====================
    @Transactional(rollbackOn = NutritionNotFoundException.class)
    public NutritionDTO updateNutrition(Long id, NutritionDTO nutritionDTO) throws NutritionNotFoundException {
        if (nutritionRepository.existsById(id)){
            Nutrition updatedNutrition = nutritionMapper.toEntity(nutritionDTO);
            nutritionRepository.save(updatedNutrition);
        }else {
            throw new NutritionNotFoundException();
        }

        return nutritionDTO;
    }

    @Transactional(rollbackOn = NutritionNotFoundException.class)
    public NutritionDTO deleteNutrition(Long id) throws NutritionNotFoundException {
        Nutrition nutrition = nutritionRepository.findById(id)
                .orElseThrow(NutritionNotFoundException::new);
        nutritionRepository.delete(nutrition);

        return nutritionMapper.toDto(nutrition);
    }

    public List<NutritionDTO> getAllNutrition() throws NutritionNotFoundException {
        List<Nutrition> allNutrition = nutritionRepository.findAll();
        if (allNutrition.isEmpty()){
            throw new NutritionNotFoundException();
        }

        return allNutrition.stream().map(nutritionMapper::toDto).toList();
    }
}
