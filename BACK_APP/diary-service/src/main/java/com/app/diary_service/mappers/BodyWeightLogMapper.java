package com.app.diary_service.mappers;

import com.app.diary_service.dtos.common.BodyWeightLogDto;
import com.app.diary_service.entities.BodyWeightLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BodyWeightLogMapper {
    @Mapping(source = "diary.id", target = "diaryId")
    BodyWeightLogDto toDto(BodyWeightLog entity);

    List<BodyWeightLogDto> toDtoList(List<BodyWeightLog> entities);
}
