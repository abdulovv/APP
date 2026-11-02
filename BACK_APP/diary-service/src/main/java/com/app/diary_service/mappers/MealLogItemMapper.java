package com.app.diary_service.mappers;

import com.app.diary_service.dtos.responses.MealLogItemResponse;
import com.app.diary_service.entities.MealLogItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MealLogItemMapper {
    MealLogItemResponse toResponse(MealLogItem mealLogItem);
}
