package com.app.diary_service.dtos;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class NoteContentDto {
    private Long id;
    private Long noteId;
    private Long nutritionId;
    private BigDecimal weight;
}
