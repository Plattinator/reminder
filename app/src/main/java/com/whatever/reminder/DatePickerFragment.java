package com.whatever.reminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

// Based on example from https://developer.android.com/guide/topics/ui/controls/pickers

public class DatePickerFragment extends DialogFragment {

    public DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

    }
}
