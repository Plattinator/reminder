package com.whatever.reminder;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

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
            }
        };
        dialogFragment.show(getFragmentManager(), "datePicker");
    }
}
