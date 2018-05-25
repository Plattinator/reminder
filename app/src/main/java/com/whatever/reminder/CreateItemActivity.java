package com.whatever.reminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;

public class CreateItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
    }

    public void pickDate(View view) {
        DatePickerFragment dialogFragment = new DatePickerFragment();
        dialogFragment.onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TextView dateTextView = findViewById(R.id.date_text);
                dateTextView.setText(
                        String.format(Locale.getDefault(), "%d/%d/%d", month, dayOfMonth, year));
                pickTime(view);
            }
        };
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    public void pickTime(View view) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                TextView timeTextView = findViewById(R.id.time_text);
                timeTextView.setText(
                        String.format(Locale.getDefault(), "%d:%d", hourOfDay, minute));
            }
        };
        timePickerFragment.show(getFragmentManager(), "timePicker");
    }
}
