package com.app.product_service.mappers;

import com.app.product_service.dtos.ProductDto;
import com.app.product_service.entities.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductCategoryMapper.class, ProductNutrientMapper.class})
public interface ProductMapper {
    ProductDto toDto(Product product);

    List<ProductDto> toDtoList(List<Product> products);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "nutrients", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    Product toEntity(ProductDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "nutrients", ignore = true)
    void updateEntity(ProductDto dto, @MappingTarget Product product);
}
