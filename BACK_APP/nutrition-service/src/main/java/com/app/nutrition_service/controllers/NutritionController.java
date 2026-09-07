package com.app.nutrition_service.controllers;

import com.app.nutrition_service.dto.DrinkDTO;
import com.app.nutrition_service.dto.FoodDTO;
import com.app.nutrition_service.exceptions.DrinkNotFoundException;
import com.app.nutrition_service.exceptions.DrinksNotFoundException;
import com.app.nutrition_service.exceptions.FoodNotFoundException;
import com.app.nutrition_service.services.NutritionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/nutrition")
public class NutritionController {
    private final NutritionService nutritionService;

    // ==================== DRINKS ====================
    @GetMapping("/drinks/all")
    public ResponseEntity<List<DrinkDTO>> getAllDrinks() throws DrinksNotFoundException {
        return nutritionService.getAllDrinks();
    }

    @GetMapping("/drinks/{id}")
    public DrinkDTO getDrinkById(@PathVariable Long id) throws DrinkNotFoundException {
        return nutritionService.getDrinkById(id);
    }

    @GetMapping("/drinks/category/{categoryName}")
    public List<DrinkDTO> getDrinksByCategoryName(@PathVariable("categoryName") String categoryName) throws DrinksNotFoundException {
        return nutritionService.getDrinkByCategoryName(categoryName);
    }

    @PostMapping("/drinks")
    public DrinkDTO createDrink(@RequestBody DrinkDTO dto) {
        return nutritionService.createDrink(dto);
    }

    @PutMapping("/drinks/{id}")
    public DrinkDTO updateDrink(
            @PathVariable Long id,
            @RequestBody DrinkDTO dto
    ) throws DrinkNotFoundException {
        return nutritionService.updateDrink(id, dto);
    }

    @DeleteMapping("/drinks/{id}")
    public void deleteDrink(@PathVariable Long id) {
        nutritionService.deleteDrink(id);
    }

    // ==================== FOOD ====================
    @GetMapping("/foods/all")
    public List<FoodDTO> getAllFoods() {
        return nutritionService.getAllFoods();
    }

    @GetMapping("/foods/{id}")
    public FoodDTO getFoodById(@PathVariable Long id) throws FoodNotFoundException {
        return nutritionService.getFoodById(id);
    }

    @PostMapping("/foods")
    public FoodDTO createFood(@RequestBody FoodDTO dto) {
        return nutritionService.createFood(dto);
    }

    @PutMapping("/foods/{id}")
    public FoodDTO updateFood(
            @PathVariable Long id,
            @RequestBody FoodDTO dto
    ) throws FoodNotFoundException {
        return nutritionService.updateFood(id, dto);
    }

    @DeleteMapping("/foods/{id}")
    public void deleteFood(@PathVariable Long id) throws FoodNotFoundException {
        nutritionService.deleteFood(id);
    }

}
