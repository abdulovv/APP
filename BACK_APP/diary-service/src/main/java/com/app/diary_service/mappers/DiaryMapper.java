package com.app.diary_service.mappers;

import com.app.diary_service.dtos.responses.DiaryResponse;
import com.app.diary_service.entities.Diary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiaryMapper {
    DiaryResponse toResponse(Diary diary);
}
