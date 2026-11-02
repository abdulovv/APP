package com.app.product_service.mappers;

import com.app.product_service.dtos.NutrientResponse;
import com.app.product_service.entities.Nutrient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NutrientMapper {
    NutrientResponse toResponse(Nutrient nutrient);

    List<NutrientResponse> toResponseList(List<Nutrient> nutrients);
}
