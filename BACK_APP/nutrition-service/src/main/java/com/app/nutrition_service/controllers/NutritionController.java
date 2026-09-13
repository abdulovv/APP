package com.app.nutrition_service.controllers;

import com.app.nutrition_service.dto.NutritionDTO;
import com.app.nutrition_service.exceptions.*;
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
    public ResponseEntity<List<NutritionDTO>> getAllDrinks() throws DrinksNotFoundException {
        return nutritionService.getAllDrinks();
    }

    @GetMapping("/drinks/category/{categoryName}")
    public List<NutritionDTO> getDrinksByCategoryName(@PathVariable("categoryName") String categoryName) throws DrinksNotFoundException {
        return nutritionService.getDrinksByCategoryName(categoryName);
    }

    @PostMapping("/drinks")
    public NutritionDTO createDrink(@RequestBody NutritionDTO dto) throws NoNutrientsException {
        return nutritionService.createDrink(dto);
    }

    // ==================== FOOD ====================
    @GetMapping("/foods/all")
    public List<NutritionDTO> getAllFoods() {
        return nutritionService.getAllFoods();
    }

    @GetMapping("/foods/category/{categoryName}")
    public List<NutritionDTO> getFoodsByCategoryName(@PathVariable("categoryName") String categoryName) throws FoodNotFoundException {
        return nutritionService.getFoodByCategoryName(categoryName);
    }

    @PostMapping("/foods")
    public NutritionDTO createFood(@RequestBody NutritionDTO dto) throws NoNutrientsException {
        return nutritionService.createFood(dto);
    }

    // ==================== GENERAL ====================
    @GetMapping("/all")
    public List<NutritionDTO> getAllNutrition() throws NutritionNotFoundException {
        return nutritionService.getAllNutrition();
    }

    @PutMapping("/update/{id}")
    public NutritionDTO updateNutrition(
            @PathVariable(name = "id") Long id,
            @RequestBody NutritionDTO nutritionDTO
    ) throws NutritionNotFoundException {
        return nutritionService.updateNutrition(id, nutritionDTO);
    }

    @DeleteMapping("/delete/{id}")
    public NutritionDTO deleteNutrition(@PathVariable(name = "id") Long id) throws NutritionNotFoundException {
        return nutritionService.deleteNutrition(id);
    }
}
