package com.app.diary_service.mappers;

import com.app.diary_service.dtos.common.MealLogsInfoDto;
import com.app.diary_service.dtos.responses.MealLogsInfoResponse;
import com.app.diary_service.entities.MealLogsInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MealLogsInfoMapper {
    @Mapping(source = "mealLog.id", target = "mealLogId")
    MealLogsInfoDto toDto(MealLogsInfo entity);

    MealLogsInfoResponse toResponse(MealLogsInfo entity);
}
