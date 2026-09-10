package com.app.nutrition_service.services.subservices;

import com.app.nutrition_service.dto.NutrientValueDTO;
import com.app.nutrition_service.dto.NutritionDTO;
import com.app.nutrition_service.entities.*;
import com.app.nutrition_service.exceptions.DrinkNotFoundException;
import com.app.nutrition_service.exceptions.DrinksNotFoundException;
import com.app.nutrition_service.exceptions.NoNutrientsException;
import com.app.nutrition_service.exceptions.NutrientNotFoundException;
import com.app.nutrition_service.mappers.NutritionMapper;
import com.app.nutrition_service.repositories.NutrientRepository;
import com.app.nutrition_service.repositories.NutritionRepository;
import com.app.nutrition_service.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DrinkService {
    private final NutritionRepository nutritionRepository;
    private final NutrientRepository nutrientRepository;
    private final CategoryService categoryService;
    private final NutritionMapper nutritionMapper;

    public ResponseEntity<List<NutritionDTO>> getAllDrinks() throws DrinksNotFoundException {
        List<Nutrition> drinks = nutritionRepository.findByCategoryType(CategoryType.DRINK);
        if (drinks.isEmpty()) {
            throw new DrinksNotFoundException();
        }

        return ResponseEntity.ok(nutritionMapper.toDtoList(drinks));
    }

    public List<NutritionDTO> getDrinksByCategoryName(String categoryName) throws DrinksNotFoundException {
        List<Nutrition> drinks =
                nutritionRepository.findByCategoryNameAndCategoryType(categoryName, CategoryType.DRINK);

        if (drinks.isEmpty()) {
            throw new DrinksNotFoundException();
        }

        return nutritionMapper.toDtoList(drinks);
    }

    @Transactional(rollbackFor = NoNutrientsException.class)
    public NutritionDTO createDrink(NutritionDTO dto) throws NoNutrientsException {
        Nutrition drink = nutritionMapper.toEntity(dto);
        Category category = categoryService.checkCategory(dto.getCategory(), CategoryType.DRINK);
        drink.setCategory(category);

        nutritionRepository.save(drink);
        addNutrients(drink, dto.getNutrients());

        return nutritionMapper.toDto(drink);
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
