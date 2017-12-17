package com.ruslanabzalov.pocketdoc.disease;

import android.app.Activity;
import android.app.Dialog;
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

/**
 * Класс, отвечающий за создание и настройку AlertDialog.
 */
public class DiseaseDatePickerFragment extends DialogFragment {

    public static final String EXTRA_DISEASE_DATE = "com.ruslanabzalov.pocketdoc.date";

    /**
     * Константа-ключ для упаковки аргумента, связанного с датой начала заболевания.
     */
    private static final String ARG_DISEASE_DATE = "disease date";

    private DatePicker mDiseaseDatePicker;

    /**
     * Метод, создающий экземпляр фрагмента DiseaseDatePickerFragment,
     * а также упаковывающий и задающий его аргументы.
     * @param date
     * @return
     */
    public static DiseaseDatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DISEASE_DATE, date);
        DiseaseDatePickerFragment fragment = new DiseaseDatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Метод, создающий AlertDialog для выбора даты начала лечения с заголовком и одной кнопкой Ok.
     * @param savedInstanceState
     * @return
     */
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
                // Отправка результата после нажатия на кнопку Ok
                .setPositiveButton(android.R.string.ok,
                        (dialogInterface, i) -> {
                            int year1 = mDiseaseDatePicker.getYear();
                            int month1 = mDiseaseDatePicker.getMonth();
                            int day1 = mDiseaseDatePicker.getDayOfMonth();
                            Date date1 = new GregorianCalendar(year1, month1, day1).getTime();
                            sendResult(Activity.RESULT_OK, date1);
                        })
                .create();
    }

    /**
     * Метод, возвращающий результат целевому фрагменту.
     * @param resultCode
     * @param date
     */
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DISEASE_DATE, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
