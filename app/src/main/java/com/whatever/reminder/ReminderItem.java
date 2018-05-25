package com.whatever.reminder;

import java.time.LocalDateTime;

public class ReminderItem {
    public String message;
    public LocalDateTime dateTime;
    public boolean repeats;
    public int repeatValue;
    public RepeatUnits repeatUnits;

    ReminderItem() {
        message = "";
        dateTime = LocalDateTime.of(1, 1, 1, 1, 1);
    }

    public ReminderItem(String message, LocalDateTime dateTime, boolean repeats, int repeatValue, RepeatUnits repeatUnits) {
        this.message = message;
        this.dateTime = dateTime;
        this.repeats = repeats;
        this.repeatValue = repeatValue;
        this.repeatUnits = repeatUnits;
    }
}
