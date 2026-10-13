package com.app.nutrition_service.mappers;

import com.app.nutrition_service.dto.CategoryDTO;
import com.app.nutrition_service.entities.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category category);

    List<CategoryDTO> toDtoList(List<Category> categories);

    Category toEntity(CategoryDTO dto);
}
