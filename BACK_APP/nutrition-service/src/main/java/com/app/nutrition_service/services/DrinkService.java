package com.app.nutrition_service.services;

import com.app.nutrition_service.dto.DrinkDTO;
import com.app.nutrition_service.mappers.DrinkMapper;
import com.app.nutrition_service.entities.Drink;
import com.app.nutrition_service.exceptions.DrinkNotFoundException;
import com.app.nutrition_service.exceptions.DrinksNotFoundException;
import com.app.nutrition_service.repositories.CategoryRepository;
import com.app.nutrition_service.repositories.DrinkRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DrinkService {
    private final DrinkRepository drinkRepository;
    private final CategoryRepository categoryRepository;
    private final DrinkMapper drinkMapper;


    public ResponseEntity<List<DrinkDTO>> getAllDrinks() throws DrinksNotFoundException {
        List<Drink> drinks = drinkRepository.findAll();
        if (drinks.isEmpty()){
            throw new DrinksNotFoundException();
        }

        return ResponseEntity.ok(
                drinkMapper.toDtoList(drinks)
        );
    }

    public DrinkDTO getDrinkById(Long id) throws DrinkNotFoundException {
        Drink drink = drinkRepository.findById(id).orElseThrow(DrinkNotFoundException::new);

        return drinkMapper.toDto(drink);
    }

    public List<DrinkDTO> getDrinksByCategoryName(String categoryName) throws DrinksNotFoundException {
        categoryName = categoryName.toUpperCase();
        List<Drink> drinks = drinkRepository.findByCategoryName(categoryName);
        if (drinks.isEmpty()){
            throw new DrinksNotFoundException();
        }

        return drinkMapper.toDtoList(drinks);
    }

    public DrinkDTO createDrink(DrinkDTO dto) {
        Drink drink = drinkMapper.toEntity(dto);
        Drink savedDrink = drinkRepository.save(drink);

        return drinkMapper.toDto(savedDrink);
    }

    public DrinkDTO updateDrink(Long id, DrinkDTO dto) throws DrinkNotFoundException {
        Drink drink = drinkRepository.findById(id).orElseThrow(DrinkNotFoundException::new);
        drinkMapper.updateEntity(dto, drink);
        Drink updatedDrink = drinkRepository.save(drink);

        return drinkMapper.toDto(updatedDrink);
    }

    public void deleteDrink(Long id) {
        if (!drinkRepository.existsById(id)) {
            throw new RuntimeException("Drink not found");
        }

        drinkRepository.deleteById(id);
    }

}
