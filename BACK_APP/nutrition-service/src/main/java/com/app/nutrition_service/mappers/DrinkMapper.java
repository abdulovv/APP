package com.app.nutrition_service.mappers;

import com.app.nutrition_service.dto.DrinkDTO;
import com.app.nutrition_service.entities.Drink;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface DrinkMapper {
    DrinkDTO toDto(Drink drink);

    List<DrinkDTO> toDtoList(List<Drink> drinks);

    Drink toEntity(DrinkDTO dto);

    List<Drink> toEntityList(List<DrinkDTO> drinksDto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(DrinkDTO dto, @MappingTarget Drink drink);
}