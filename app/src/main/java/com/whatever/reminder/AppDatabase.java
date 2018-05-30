package com.whatever.reminder;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ReminderItem.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ReminderItemDao reminderItemDao();
}
