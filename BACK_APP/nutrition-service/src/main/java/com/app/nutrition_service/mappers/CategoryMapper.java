package com.app.nutrition_service.mappers;

import com.app.nutrition_service.dto.CategoryDTO;
import com.app.nutrition_service.entities.Category;
import com.app.nutrition_service.entities.CategoryType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "type")
    CategoryDTO toDto(Category category);

    @Mapping(target = "type")
    Category toEntity(CategoryDTO dto);
}
