package com.app.diary_service.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class DateUtils {
    private DateUtils() {
    }

    public static LocalDateTime startOfDay(LocalDate date) {
        return date.atStartOfDay();
    }

    public static LocalDateTime endOfDay(LocalDate date) {
        return date.plusDays(1).atStartOfDay().minusNanos(1);
    }
}
