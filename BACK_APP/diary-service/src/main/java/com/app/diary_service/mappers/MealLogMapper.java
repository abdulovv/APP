package com.app.diary_service.mappers;

import com.app.diary_service.dtos.common.MealLogDto;
import com.app.diary_service.dtos.responses.MealResponse;
import com.app.diary_service.entities.MealLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = MealLogsInfoMapper.class)
public interface MealLogMapper {
    @Mapping(source = "diary.id", target = "diaryId")
    MealLogDto toDto(MealLog entity);

    List<MealLogDto> toDtoList(List<MealLog> entities);

    MealResponse toResponse(MealLog entity);

    List<MealResponse> toResponseList(List<MealLog> entities);
}
