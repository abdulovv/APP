package com.app.nutrition_service.mappers;

import com.app.nutrition_service.dto.NutrientDTO;
import com.app.nutrition_service.entities.Nutrient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NutrientMapper {
    NutrientDTO toDto(Nutrient nutrient);

    List<NutrientDTO> toDtoList(List<Nutrient> nutrients);

    Nutrient toEntity(NutrientDTO dto);
}
