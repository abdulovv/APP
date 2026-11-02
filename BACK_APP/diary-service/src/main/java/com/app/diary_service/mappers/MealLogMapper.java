package com.app.diary_service.mappers;

import com.app.diary_service.dtos.responses.MealLogResponse;
import com.app.diary_service.entities.MealLog;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MealLogItemMapper.class)
public interface MealLogMapper {
    MealLogResponse toResponse(MealLog mealLog);

    List<MealLogResponse> toResponseList(List<MealLog> mealLogs);
}
