package com.app.diary_service.dtos;

import com.app.diary_service.entities.MealType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class NoteDto {
    private Long id;
    private Long diaryId;
    private MealType mealType;
    private LocalDateTime createdAt;
}
