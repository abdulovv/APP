package com.app.diary_service.mappers;

import com.app.diary_service.dtos.responses.BodyWeightLogResponse;
import com.app.diary_service.entities.BodyWeightLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BodyWeightLogMapper {
    @Mapping(target = "diaryId", source = "diary.id")
    BodyWeightLogResponse toResponse(BodyWeightLog bodyWeightLog);

    List<BodyWeightLogResponse> toResponseList(List<BodyWeightLog> bodyWeightLogs);
}
