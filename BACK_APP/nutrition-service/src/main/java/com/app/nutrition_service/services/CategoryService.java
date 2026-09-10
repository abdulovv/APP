package com.app.nutrition_service.services;

import com.app.nutrition_service.dto.CategoryDTO;
import com.app.nutrition_service.entities.Category;
import com.app.nutrition_service.entities.CategoryType;
import com.app.nutrition_service.exceptions.CategoryNotFoundException;
import com.app.nutrition_service.exceptions.WrongCategoryTypeException;
import com.app.nutrition_service.mappers.CategoryMapper;
import com.app.nutrition_service.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDTO> getAllByType(CategoryType type) {
        return categoryRepository.findByType(type).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public Category checkCategory(CategoryDTO categoryDTO, CategoryType expectedType) {
        Category category = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(CategoryNotFoundException::new);

        if (category.getType() != expectedType) {
            throw new WrongCategoryTypeException("Category has unexpected type");
        }

        return category;
    }
}
