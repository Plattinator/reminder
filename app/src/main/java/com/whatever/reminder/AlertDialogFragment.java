package com.whatever.reminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;

public class AlertDialogFragment extends DialogFragment {

    public static final String MESSAGE_KEY = "message";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle arguments = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(arguments.getString(MESSAGE_KEY));
        return builder.create();
    }

    public static void show(String message, FragmentManager fragmentManager) {
        Bundle arguments = new Bundle();
        arguments.putString(MESSAGE_KEY, message);
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.setArguments(arguments);
        dialogFragment.show(fragmentManager, "showAlert");
    }
}
