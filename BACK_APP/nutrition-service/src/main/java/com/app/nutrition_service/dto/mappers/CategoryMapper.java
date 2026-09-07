package com.app.nutrition_service.dto.mappers;

import com.app.nutrition_service.dto.CategoryDTO;
import com.app.nutrition_service.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category category);

    Category toEntity(CategoryDTO dto);
}
