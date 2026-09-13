package com.app.diary_service.mappers;

import com.app.diary_service.dtos.NoteContentDto;
import com.app.diary_service.entities.Note;
import com.app.diary_service.entities.NoteContent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface NoteContentMapper {

    @Mapping(source = "note.id", target = "noteId")
    NoteContentDto toDto(NoteContent entity);

    @Mapping(source = "noteId", target = "note", qualifiedByName = "noteFromId")
    NoteContent toEntity(NoteContentDto dto);

    @Named("noteFromId")
    default Note noteFromId(Long id) {
        if (id == null) {
            return null;
        }
        Note note = new Note();
        note.setId(id);
        return note;
    }
}
