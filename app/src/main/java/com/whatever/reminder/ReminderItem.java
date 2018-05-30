package com.whatever.reminder;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity(tableName = "reminder_items")
public class ReminderItem implements Parcelable {

    @PrimaryKey(autoGenerate = true)
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

    private ReminderItem(@NotNull Parcel in) {
        id = in.readInt();
        message = in.readString();
        dateTime = new LocalDateTimeConverter().stringToLocalDateTime(in.readString());
        repeats = in.readByte() != 0;
        repeatValue = in.readInt();
        repeatUnits = new RepeatUnitsConverter().stringToRepeatUnits(in.readString());
    }

    public static final Creator<ReminderItem> CREATOR = new Creator<ReminderItem>() {
        @NonNull
        @Override
        public ReminderItem createFromParcel(Parcel in) {
            return new ReminderItem(in);
        }

        @NonNull
        @Contract(pure = true)
        @Override
        public ReminderItem[] newArray(int size) {
            return new ReminderItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(message);
        dest.writeString(new LocalDateTimeConverter().localDateTimeToString(dateTime));
        dest.writeByte((byte) (repeats ? 1 : 0));
        dest.writeInt(repeatValue);
        dest.writeString(new RepeatUnitsConverter().repeatUnitsToString(repeatUnits));
    }
}
