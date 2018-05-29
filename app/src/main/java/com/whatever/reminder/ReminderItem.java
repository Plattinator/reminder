package com.whatever.reminder;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.time.LocalDateTime;

@Entity(tableName = "reminder_items")
public class ReminderItem {

    @PrimaryKey
    public int id;

    public String message;
    @TypeConverters(LocalDateTimeConverter.class)
    public LocalDateTime dateTime;
    public boolean repeats;
    public int repeatValue;
    @TypeConverters(RepeatUnitsConverter.class)
    public RepeatUnits repeatUnits;

    ReminderItem() {
        message = "";
        dateTime = LocalDateTime.of(1, 1, 1, 1, 1);
    }

    ReminderItem(String message, LocalDateTime dateTime, boolean repeats, int repeatValue, RepeatUnits repeatUnits) {
        this.message = message;
        this.dateTime = dateTime;
        this.repeats = repeats;
        this.repeatValue = repeatValue;
        this.repeatUnits = repeatUnits;
    }
}
