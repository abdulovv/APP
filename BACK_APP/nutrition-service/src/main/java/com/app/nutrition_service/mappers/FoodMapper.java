package com.app.nutrition_service.mappers;

import com.app.nutrition_service.dto.FoodDTO;
import com.app.nutrition_service.entities.Food;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface FoodMapper {

    FoodDTO toDto(Food food);

    Food toEntity(FoodDTO dto);

    List<FoodDTO> toDtoList(List<Food> foods);

    List<Food> toEntityList(List<FoodDTO> dtos);

    @Mapping(target = "id", ignore = true)
    void updateEntity(FoodDTO dto, @MappingTarget Food food);
}

