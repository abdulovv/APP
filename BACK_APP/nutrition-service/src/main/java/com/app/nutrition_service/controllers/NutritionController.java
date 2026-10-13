package com.app.nutrition_service.controllers;

import com.app.nutrition_service.dto.CategoryDTO;
import com.app.nutrition_service.dto.NutrientDTO;
import com.app.nutrition_service.dto.NutritionDTO;
import com.app.nutrition_service.entities.CategoryType;
import com.app.nutrition_service.services.CategoryService;
import com.app.nutrition_service.services.NutrientService;
import com.app.nutrition_service.services.NutritionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nutrition")
public class NutritionController {
    private final NutritionService nutritionService;
    private final CategoryService categoryService;
    private final NutrientService nutrientService;

    private static boolean isAdmin(String role){
        return "ADMIN".equals(role);
    }

    // ==================== DRINKS ====================
    @GetMapping("/drinks")
    public ResponseEntity<List<NutritionDTO>> getAllDrinks() {
        return ResponseEntity.ok(nutritionService.getAllDrinks());
    }

    @GetMapping("/drinks/category/{categoryName}")
    public ResponseEntity<List<NutritionDTO>> getDrinksByCategoryName(@PathVariable("categoryName") String categoryName) {
        return ResponseEntity.ok(nutritionService.getDrinksByCategoryName(categoryName));
    }

    @PostMapping("/drinks")
    public ResponseEntity<NutritionDTO> createDrink(
            @RequestBody NutritionDTO dto,
            @RequestHeader("X-User-Role") String role
    ){
        if (!isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nutritionService.createDrink(dto));
    }

    // ==================== FOOD ====================
    @GetMapping("/foods")
    public ResponseEntity<List<NutritionDTO>> getAllFoods() {
        return ResponseEntity.ok(nutritionService.getAllFoods());
    }

    @GetMapping("/foods/category/{categoryName}")
    public ResponseEntity<List<NutritionDTO>> getFoodsByCategoryName(@PathVariable("categoryName") String categoryName) {
        return ResponseEntity.ok(nutritionService.getFoodsByCategoryName(categoryName));
    }

    @PostMapping("/foods")
    public ResponseEntity<NutritionDTO> createFood(
            @RequestBody NutritionDTO dto,
            @RequestHeader("X-User-Role") String role
    ) {
        if (!isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nutritionService.createFood(dto));
    }

    // ==================== DICTIONARIES ====================
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories(@RequestParam(name = "type", required = false) CategoryType type) {
        List<CategoryDTO> categories = type == null
                ? categoryService.getAll()
                : categoryService.getAllByType(type);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/nutrients")
    public ResponseEntity<List<NutrientDTO>> getNutrients() {
        return ResponseEntity.ok(nutrientService.getAll());
    }

    // ==================== GENERAL ====================
    @GetMapping
    public ResponseEntity<List<NutritionDTO>> getAllNutrition() {
        return ResponseEntity.ok(nutritionService.getAllNutrition());
    }

    @GetMapping("/search")
    public ResponseEntity<List<NutritionDTO>> searchNutrition(@RequestParam("query") String query) {
        return ResponseEntity.ok(nutritionService.searchNutrition(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NutritionDTO> getNutritionById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(nutritionService.getNutritionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NutritionDTO> updateNutrition(
            @PathVariable("id") Long id,
            @RequestBody NutritionDTO nutritionDTO,
            @RequestHeader("X-User-Role") String role
    ) {
        if (!isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(nutritionService.updateNutrition(id, nutritionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNutrition(
            @PathVariable("id") Long id,
            @RequestHeader("X-User-Role") String role
    ) {
        if (!isAdmin(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        nutritionService.deleteNutrition(id);
        return ResponseEntity.noContent().build();
    }


}
