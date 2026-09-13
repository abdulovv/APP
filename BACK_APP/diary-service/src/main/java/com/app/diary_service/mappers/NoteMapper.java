package com.app.diary_service.mappers;

import com.app.diary_service.dtos.NoteDto;
import com.app.diary_service.entities.Diary;
import com.app.diary_service.entities.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    @Mapping(source = "diary.id", target = "diaryId")
    NoteDto toDto(Note entity);

    @Mapping(source = "diaryId", target = "diary", qualifiedByName = "diaryFromId")
    Note toEntity(NoteDto dto);

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
