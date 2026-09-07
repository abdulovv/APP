package com.app.nutrition_service.services;

import com.app.nutrition_service.dto.CategoryDTO;
import com.app.nutrition_service.dto.DrinkDTO;
import com.app.nutrition_service.dto.FoodDTO;
import com.app.nutrition_service.exceptions.DrinkNotFoundException;
import com.app.nutrition_service.exceptions.DrinksNotFoundException;
import com.app.nutrition_service.exceptions.FoodNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NutritionService {
    private final FoodService foodService;
    private final DrinkService drinkService;
    private final CategoryService categoryService;

    // ==================== DRINK ====================
    public ResponseEntity<List<DrinkDTO>> getAllDrinks() throws DrinksNotFoundException {
        return drinkService.getAllDrinks();
    }

    public DrinkDTO getDrinkById(Long id) throws DrinkNotFoundException {
        return drinkService.getDrinkById(id);
    }

    public List<DrinkDTO> getDrinkByCategoryName(String categoryName) throws DrinksNotFoundException {
        return drinkService.getDrinksByCategoryName(categoryName);
    }

    public DrinkDTO createDrink(DrinkDTO dto) {
        return drinkService.createDrink(dto);
    }

    public DrinkDTO updateDrink(Long id, DrinkDTO dto) throws DrinkNotFoundException {
        return drinkService.updateDrink(id, dto);
    }

    public void deleteDrink(Long id) {
        drinkService.deleteDrink(id);
    }

    // ==================== FOOD ====================
    public List<FoodDTO> getAllFoods() {
        return foodService.getAllFoods();
    }

    public FoodDTO getFoodById(Long id) throws FoodNotFoundException {
        return foodService.getFoodById(id);
    }

    public FoodDTO createFood(FoodDTO dto) {
        return foodService.createFood(dto);
    }

    public FoodDTO updateFood(Long id, FoodDTO dto) throws FoodNotFoundException {
        return foodService.updateFood(id, dto);
    }

    public void deleteFood(Long id) throws FoodNotFoundException {
        foodService.deleteFood(id);
    }


}
