package com.app.nutrition_service.mappers;

import com.app.nutrition_service.dto.NutritionDTO;
import com.app.nutrition_service.entities.Nutrition;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, NutritionNutrientMapper.class})
public interface NutritionMapper {
    @Mapping(target = "nutrients", source = "nutrients")
    NutritionDTO toDto(Nutrition nutrition);

    List<NutritionDTO> toDtoList(List<Nutrition> nutritionList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "nutrients", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    Nutrition toEntity(NutritionDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "nutrients", ignore = true)
    void updateEntity(NutritionDTO dto, @MappingTarget Nutrition entity);
}
