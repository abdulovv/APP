package com.app.diary_service.dtos;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class DiaryDto {
    private Long id;
    private Long userId;
    private BigDecimal height;
    private LocalDate birthDate;
}
