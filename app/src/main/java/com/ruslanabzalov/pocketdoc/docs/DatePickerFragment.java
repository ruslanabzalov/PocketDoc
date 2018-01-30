package com.ruslanabzalov.pocketdoc.docs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.ruslanabzalov.pocketdoc.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public static final String EXTRA_DATE = "com.ruslanabzalov.pocketdoc.docs.date";

    private DatePicker mDatePicker;

    private EditNameDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        mDatePicker = view.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year, month, day, null);
        mDatePicker.setMinDate(System.currentTimeMillis() - 1000);
        mDatePicker.setMaxDate(System.currentTimeMillis() + 1210000000);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Выберите желаемую дату записи")
                .setPositiveButton(android.R.string.ok, (DialogInterface dialog, int which) -> {
                    int year1 = mDatePicker.getYear();
                    int month1 = mDatePicker.getMonth();
                    int day1 = mDatePicker.getDayOfMonth();
                    Date date1 = new GregorianCalendar(year1, month1, day1).getTime();
                    sendResult(Activity.RESULT_OK, date1);
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
