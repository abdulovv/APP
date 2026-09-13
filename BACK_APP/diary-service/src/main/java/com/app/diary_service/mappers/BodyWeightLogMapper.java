package com.app.diary_service.mappers;

import com.app.diary_service.dtos.BodyWeightLogDto;
import com.app.diary_service.entities.BodyWeightLog;
import com.app.diary_service.entities.Diary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BodyWeightLogMapper {

    @Mapping(source = "diary.id", target = "diaryId")
    BodyWeightLogDto toDto(BodyWeightLog entity);

    @Mapping(source = "diaryId", target = "diary", qualifiedByName = "diaryFromId")
    BodyWeightLog toEntity(BodyWeightLogDto dto);

        @Named("diaryFromId")
    default Diary diaryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Diary diary = new Diary();
        diary.setId(id);
        return diary;
    }
}
