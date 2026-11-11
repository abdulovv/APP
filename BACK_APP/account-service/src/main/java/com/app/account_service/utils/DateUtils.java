package com.app.account_service.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateUtils {
    private static final DateTimeFormatter LONG_DATE =
            DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH).withZone(ZoneId.of("UTC"));

    private DateUtils() {
    }

    public static String formatLongDate(Instant instant) {
        return instant == null ? "—" : LONG_DATE.format(instant);
    }
}
