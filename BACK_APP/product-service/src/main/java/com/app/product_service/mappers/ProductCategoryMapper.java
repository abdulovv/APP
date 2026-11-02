package com.app.product_service.mappers;

import com.app.product_service.dtos.ProductCategoryDto;
import com.app.product_service.entities.ProductCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {
    ProductCategoryDto toDto(ProductCategory category);

    List<ProductCategoryDto> toDtoList(List<ProductCategory> categories);
}
