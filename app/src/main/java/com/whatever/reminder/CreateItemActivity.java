package com.whatever.reminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

public class CreateItemActivity extends AppCompatActivity {

    public OnCreateItemListener createItemListener;

    private LocalDate mDate;
    private LocalTime mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        Spinner frequencyUnitSpinner = findViewById(R.id.every_frequency_unit);
        ArrayAdapter<CharSequence> frequencyUnitAdapter = ArrayAdapter.createFromResource(
                this, R.array.frequency, android.R.layout.simple_spinner_item);
        frequencyUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencyUnitSpinner.setAdapter(frequencyUnitAdapter);
    }

    public void pickDate(View view) {
        DatePickerFragment dialogFragment = new DatePickerFragment();
        dialogFragment.onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mDate = LocalDate.of(year, month + 1, dayOfMonth);
                TextView dateTextView = findViewById(R.id.date_text);
                dateTextView.setText(String.format(Locale.getDefault(), "%tD", mDate));
                if (mTime == null) {
                    pickTime(view);
                }
            }
        };
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    public void pickTime(View view) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mTime = LocalTime.of(hourOfDay, minute);
                TextView timeTextView = findViewById(R.id.time_text);
                timeTextView.setText(String.format(Locale.getDefault(),
                        "%tl:%tM %tp", mTime, mTime, mTime));
            }
        };
        timePickerFragment.show(getFragmentManager(), "timePicker");
    }

    public void onDone(View view) {
        if (mDate == null && mTime == null) {
            AlertDialogFragment.show("Pick a date and time, ya dingus!", getFragmentManager());
        }
        else if (mDate == null) {
            AlertDialogFragment.show("I need to know the date for this thing!", getFragmentManager());
        }
        else if (mTime == null) {
            AlertDialogFragment.show("What time??", getFragmentManager());
        }
        else {
            String message = ((EditText)findViewById(R.id.message_text)).getText().toString();
            LocalDateTime dateTime = LocalDateTime.of(mDate, mTime);
            boolean repeats = ((CheckBox)findViewById(R.id.repeats_checkbox)).isChecked();
            int repeatValue = Integer.parseInt(((EditText)findViewById(R.id.every_number)).getText().toString());
            RepeatUnits repeatUnits = RepeatUnits.values()[((Spinner)findViewById(R.id.every_frequency_unit)).getSelectedItemPosition()];

            if (dateTime.isBefore(LocalDateTime.now())) {
                AlertDialogFragment.show("I can't remind you of something in the past!", getFragmentManager());
            }
            else {
                createItemListener.onReminderItemCreated(new ReminderItem(message, dateTime, repeats, repeatValue, repeatUnits));
            }
        }
    }

    public interface OnCreateItemListener {
        void onReminderItemCreated(ReminderItem reminderItem);
    }
}
