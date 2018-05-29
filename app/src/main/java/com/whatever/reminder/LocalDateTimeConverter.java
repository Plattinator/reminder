package com.whatever.reminder;

import android.arch.persistence.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @TypeConverter
    public LocalDateTime stringToLocalDateTime(String value) {
        return LocalDateTime.parse(value, FORMATTER);
    }

    @TypeConverter
    public String localDateTimeToString(LocalDateTime value) {
        return value.format(FORMATTER);
    }
}
