package com.app.product_service.mappers;

import com.app.product_service.dtos.ProductNutrientDto;
import com.app.product_service.entities.ProductNutrient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductNutrientMapper {

    @Mapping(target = "nutrientId", source = "nutrient.id")
    @Mapping(target = "name", source = "nutrient.name")
    @Mapping(target = "unit", source = "nutrient.unit")
    ProductNutrientDto toDto(ProductNutrient productNutrient);

    List<ProductNutrientDto> toDtoList(List<ProductNutrient> productNutrients);
}
