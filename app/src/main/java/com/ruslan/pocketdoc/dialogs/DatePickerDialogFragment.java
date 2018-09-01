package com.ruslan.pocketdoc.dialogs;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.StringUtils;
import com.ruslan.pocketdoc.doctors.DoctorsFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class DatePickerDialogFragment extends DialogFragment {

    private static final String ARG_SPECIALITY_ID = "speciality_id";
    private static final String ARG_STATION_ID = "station_id";

    private DatePicker mDatePicker;

    private String mSpecialityId;
    private String mStationId;

    public static DialogFragment newInstance(String specialityId, String stationId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_SPECIALITY_ID, specialityId);
        arguments.putString(ARG_STATION_ID, stationId);
        DialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        datePickerDialogFragment.setArguments(arguments);
        return datePickerDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpecialityId = Objects.requireNonNull(getArguments()).getString(ARG_SPECIALITY_ID);
        mStationId = Objects.requireNonNull(getArguments()).getString(ARG_STATION_ID);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_fragment_date_picker, null);
        mDatePicker = rootView.findViewById(R.id.date_picker);
        int thirteenDaysInMillis = 1123000000;
        mDatePicker.setMinDate(System.currentTimeMillis());
        mDatePicker.setMaxDate(System.currentTimeMillis() + thirteenDaysInMillis);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mDatePicker.setOnDateChangedListener(((datePicker, i, i1, i2) -> showDoctorsListUi()));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_dialog_fragment_title)
                .setView(rootView)
                .setPositiveButton(R.string.date_picker_dialog_fragment_positive_button_text,
                        (dialogInterface, i) -> showDoctorsListUi())
                .setNegativeButton(R.string.date_picker_dialog_fragment_negative_button_text,
                        (dialogInterface, i) -> dismiss());
        Dialog datePickerDialog = builder.create();
        datePickerDialog.setCanceledOnTouchOutside(false);
        return datePickerDialog;
    }

    private void showDoctorsListUi() {
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        String correctDate = StringUtils.makeCorrectDateString(date);
        FragmentManager fragmentManager =
                Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        Fragment doctorsFragment =
                DoctorsFragment.newInstance(mSpecialityId, mStationId, correctDate);
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, doctorsFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
        dismiss();
    }
}
