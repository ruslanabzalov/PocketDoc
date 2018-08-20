package com.ruslan.pocketdoc;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Класс, предоставляющий вспомогательные статические методы
 * для формирования корректных строковых литералов.
 */
public class StringUtils {

    /**
     * Метод формирования корректной строки специальности(-ей) врача.
     * @param specialities Список специальностей.
     * @return Строка специальности(-ей).
     */
    public static String getCorrectSpecialitiesString(List<Speciality> specialities) {
        if (specialities == null || specialities.size() == 0) {
            return "Специальность не указана";
        } else if (specialities.size() == 1) {
            return specialities.get(0).getName();
        } else {
            String speciality = specialities.get(0).getName();
            StringBuilder specialitiesString = new StringBuilder(speciality);
            for (int i = 1; i < specialities.size(); i++) {
                speciality = specialities.get(i).getName().toLowerCase();
                specialitiesString.append(", ").append(speciality);
            }
            return specialitiesString.toString();
        }
    }

    /**
     * Метод формирования корректной строки стажа работы врача.
     * @param experience Стаж работы.
     * @return Строка стажа работы.
     */
    public static String getCorrectExperienceString(int experience) {
        if (experience == 0) {
            return "Нет опыта";
        } else if (experience > 10 && experience < 20) {
            return experience + " лет";
        } else if (experience % 10 == 1) {
            return experience + " год";
        } else if (experience % 10 > 1 && experience % 10 < 5) {
            return experience + " года";
        } else {
            return experience + " лет";
        }
    }

    /**
     * Метод формирования корректной строки стоимости посещения врача.
     * @param price Стоимость посещения.
     * @return Строка стоимости посещения.
     */
    public static String getCorrectPriceString(int price) {
        return (price == 0) ? "Стоимость не указана" : price + "\u20bd";
    }

    /**
     * Метод формирования корректной строки, содержащей желаемую дату записи к врачу.
     * @param date Желаемая дата записи к врачу.
     * @return Корректное строковое представление желаемой даты записи.
     */
    public static String makeCorrectDateString(@NonNull Date date) {
        if (date == null) {
            return "";
        } else {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            String year = "" + calendar.get(Calendar.YEAR);
            String month = (calendar.get(Calendar.MONTH) < 10)
                    ? "0" + (calendar.get(Calendar.MONTH) + 1)
                    : "" + calendar.get(Calendar.MONTH) + 1;
            String dayOfMonth = (calendar.get(Calendar.DAY_OF_MONTH) < 10)
                    ? "0" + calendar.get(Calendar.DAY_OF_MONTH)
                    : "" + calendar.get(Calendar.DAY_OF_MONTH);
            return year + "-" + month + "-" + dayOfMonth;
        }
    }
}
