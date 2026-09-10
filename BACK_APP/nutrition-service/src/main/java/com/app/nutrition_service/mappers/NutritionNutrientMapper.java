package com.app.nutrition_service.mappers;

import com.app.nutrition_service.dto.NutrientValueDTO;
import com.app.nutrition_service.entities.NutritionNutrient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface NutritionNutrientMapper {

    @Mapping(target = "nutrientId", source = "nutrient.id")
    @Mapping(target = "name", source = "nutrient.name")
    @Mapping(target = "unit", source = "nutrient.unit")
    @Mapping(target = "value", source = "value")
    NutrientValueDTO toDto(NutritionNutrient nutritionNutrient);

    List<NutrientValueDTO> toDtoList(List<NutritionNutrient> list);
}
