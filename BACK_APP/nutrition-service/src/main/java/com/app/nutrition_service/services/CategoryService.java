package com.app.nutrition_service.services;

import com.app.nutrition_service.dto.CategoryDTO;
import com.app.nutrition_service.entities.Category;
import com.app.nutrition_service.entities.CategoryType;
import com.app.nutrition_service.exceptions.CategoryNotFoundException;
import com.app.nutrition_service.exceptions.WrongCategoryTypeException;
import com.app.nutrition_service.mappers.CategoryMapper;
import com.app.nutrition_service.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAll() {
        return categoryMapper.toDtoList(categoryRepository.findAllByOrderByNameAsc());
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllByType(CategoryType type) {
        return categoryMapper.toDtoList(categoryRepository.findByTypeOrderByNameAsc(type));
    }

    public Category checkCategory(CategoryDTO categoryDTO, CategoryType expectedType) {
        if (categoryDTO == null || categoryDTO.getId() == null) {
            throw new CategoryNotFoundException();
        }

        Category category = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(CategoryNotFoundException::new);

        if (category.getType() != expectedType) {
            throw new WrongCategoryTypeException("Category has unexpected type");
        }

        return category;
    }
}
