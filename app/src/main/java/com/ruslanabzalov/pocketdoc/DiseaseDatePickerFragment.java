package com.ruslanabzalov.pocketdoc;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Класс, отвечающий за создание и настройку AlertDialog.
 * */
public class DiseaseDatePickerFragment extends DialogFragment {

    /**
     * Константа-ключ для упаковки аргумета, связанного с датой начала заболевания.
     * */
    private static final String ARG_DISEASE_DATE = "disease date";

    private DatePicker mDiseaseDatePicker;

    /**
     * Метод, создающий экземпляр фрагмента DiseaseDatePickerFragment,
     * а также упаковывающий и задающий его аргументы.
     * */
    public static DiseaseDatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DISEASE_DATE, date);
        DiseaseDatePickerFragment fragment = new DiseaseDatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Метод, создающий AlertDialog с заголовком и одной кнопкой Ok.
     * */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DISEASE_DATE);
        // Создание объекта Calendar и его определение с помощью объекта Date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.disease_dialog_date, null);
        mDiseaseDatePicker = v.findViewById(R.id.disease_date_picker);
        mDiseaseDatePicker.init(year, month, day, null);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.disease_date_picker_title)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
