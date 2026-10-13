package com.app.diary_service.mappers;

import com.app.diary_service.dtos.common.DiaryDto;
import com.app.diary_service.dtos.responses.DiaryResponse;
import com.app.diary_service.entities.Diary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MealLogMapper.class)
public interface DiaryMapper {
    DiaryDto toDto(Diary entity);

    @Mapping(source = "mealLogs", target = "meals")
    DiaryResponse toResponse(Diary entity);
}
