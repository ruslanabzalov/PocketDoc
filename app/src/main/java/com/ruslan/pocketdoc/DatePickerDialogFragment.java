package com.ruslan.pocketdoc;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.ruslan.pocketdoc.doctors.DoctorsFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerDialogFragment extends DialogFragment {

    private static final String ARG_SPECIALITY_ID = "speciality_id";
    private static final String ARG_STATION_ID = "station_id";

    private DatePicker mDatePicker;

    private String mSpecialityId;
    private String mStationId;

    public static DialogFragment newInstance(String specialityId, String stationId) {
        Bundle args = new Bundle();
        args.putString(ARG_SPECIALITY_ID, specialityId);
        args.putString(ARG_STATION_ID, stationId);

        DialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        datePickerDialogFragment.setArguments(args);
        return datePickerDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSpecialityId = getArguments().getString(ARG_SPECIALITY_ID);
            mStationId = getArguments().getString(ARG_STATION_ID);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_fragment_date_picker, null);

        mDatePicker = rootView.findViewById(R.id.date_picker);
        mDatePicker.setMinDate(System.currentTimeMillis());
        mDatePicker.setMaxDate(System.currentTimeMillis() + 1123000000);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.date_picker_dialog_fragment_title)
                .setView(rootView)
                .setPositiveButton(R.string.date_picker_dialog_fragment_positive_button_text,
                        this::showDoctorsListUi)
                .setNegativeButton(R.string.date_picker_dialog_fragment_negative_button_text,
                        (dialogInterface, i) -> dismiss());
        Dialog datePickerDialog = builder.create();
        datePickerDialog.setCanceledOnTouchOutside(false);
        return datePickerDialog;
    }

    private void showDoctorsListUi(DialogInterface dialogInterface, int i) {
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth() + 1;
        int day = mDatePicker.getDayOfMonth();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        // TODO: Передать следующему фрагменту желаемую дату посещения!
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment doctorsFragment = DoctorsFragment.newInstance(mSpecialityId, mStationId);
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, doctorsFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
        dialogInterface.dismiss();
    }
}
