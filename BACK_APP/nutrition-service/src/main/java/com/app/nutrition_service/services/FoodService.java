package com.app.nutrition_service.services;

import com.app.nutrition_service.dto.FoodDTO;
import com.app.nutrition_service.mappers.FoodMapper;
import com.app.nutrition_service.entities.Food;
import com.app.nutrition_service.exceptions.FoodNotFoundException;
import com.app.nutrition_service.repositories.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    public List<FoodDTO> getAllFoods() {
        List<Food> foods = foodRepository.findAll();

        return foodMapper.toDtoList(foods);
    }

    public FoodDTO getFoodById(Long id) throws FoodNotFoundException {
        Food food = foodRepository.findById(id).orElseThrow(FoodNotFoundException::new);

        return foodMapper.toDto(food);
    }

    public FoodDTO createFood(FoodDTO dto) {
        Food food = foodMapper.toEntity(dto);
        Food savedFood = foodRepository.save(food);

        return foodMapper.toDto(savedFood);
    }

    public FoodDTO updateFood(Long id, FoodDTO dto) throws FoodNotFoundException {
        Food food = foodRepository.findById(id).orElseThrow(FoodNotFoundException::new);
        foodMapper.updateEntity(dto, food);
        Food updatedFood = foodRepository.save(food);

        return foodMapper.toDto(updatedFood);
    }

    public void deleteFood(Long id) throws FoodNotFoundException {
        if (!foodRepository.existsById(id)) {
            throw new FoodNotFoundException();
        }

        foodRepository.deleteById(id);
    }
}

