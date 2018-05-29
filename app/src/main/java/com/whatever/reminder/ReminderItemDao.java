package com.whatever.reminder;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface ReminderItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReminderItems(ReminderItem... reminderItems);

    @Update
    void updateReminderItems(ReminderItem... reminderItems);

    @Delete
    void deleteReminderItems(ReminderItem... reminderItems);

    @Query("SELECT * FROM reminder_items")
    ReminderItem[] loadAllReminderItems();
}
