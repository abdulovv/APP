package com.app.diary_service.mappers;

import com.app.diary_service.dtos.DiaryDto;
import com.app.diary_service.entities.Diary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiaryMapper {

    DiaryDto toDto(Diary entity);

    Diary toEntity(DiaryDto dto);
}
