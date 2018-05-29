package com.whatever.reminder;

import android.arch.persistence.room.TypeConverter;

public enum RepeatUnits {
    MINUTES,
    HOURS,
    DAYS,
}

class RepeatUnitsConverter {

    @TypeConverter
    public RepeatUnits stringToRepeatUnits(String value) {
        return RepeatUnits.valueOf(value);
    }

    @TypeConverter
    public String repeatUnitsToString(RepeatUnits value) {
        return value.toString();
    }
}