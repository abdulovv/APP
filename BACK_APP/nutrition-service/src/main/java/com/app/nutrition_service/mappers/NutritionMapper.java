package com.app.nutrition_service.mappers;

import com.app.nutrition_service.dto.NutritionDTO;
import com.app.nutrition_service.entities.Nutrition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, NutritionNutrientMapper.class})
public interface NutritionMapper {
    @Mapping(target = "nutrients", source = "nutrients")
    NutritionDTO toDto(Nutrition nutrition);

    List<NutritionDTO> toDtoList(List<Nutrition> nutritionList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "nutrients", ignore = true)
    Nutrition toEntity(NutritionDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "nutrients", ignore = true)
    void updateEntity(NutritionDTO dto, @MappingTarget Nutrition entity);
}
