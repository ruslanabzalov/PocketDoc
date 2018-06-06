package com.ruslan.pocketdoc.searching.records;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;

import com.ruslan.pocketdoc.R;

import java.util.Calendar;
import java.util.Locale;

public class SessionDateDialogFragment extends DialogFragment {

    private static final int CALENDAR_DIALOG_REQUEST = 1;
    private static final int SEVEN_DAYS_MILLIS = 604800000;
    private static final String DATE_PICKER_DATE = "date_picker_date";

    private String mDate;

    public static String getSessionDateDialogFragmentResult(Intent data) {
        return data.getStringExtra(DATE_PICKER_DATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_date_picker, null);
        initDatePicker(dialogView);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage("Выберите дату приёма")
                .setView(dialogView)
                .setPositiveButton(R.string.ok, this::onPositiveButtonClick)
                .setNegativeButton(R.string.cancel, this::onNegativeButtonClick);
        return builder.create();
    }

    private void initDatePicker(View view) {
        DatePicker datePicker = view.findViewById(R.id.session_date_picker);
        long currentTimeMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        datePicker.init(
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this::onDateChanged
        );
        datePicker.setMinDate(currentTimeMillis);
        datePicker.setMaxDate(currentTimeMillis + SEVEN_DAYS_MILLIS);
    }

    private void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        mDate = String.format(Locale.getDefault(), "%d:%d:%d", year, monthOfYear + 1, dayOfMonth);
    }

    private void onPositiveButtonClick(DialogInterface dialogInterface, int which) {
        Intent data = new Intent();
        data.putExtra(DATE_PICKER_DATE, mDate);
        getTargetFragment().onActivityResult(CALENDAR_DIALOG_REQUEST, Activity.RESULT_OK, data);
    }

    private void onNegativeButtonClick(DialogInterface dialogInterface, int which) {
        dismiss();
    }
}
