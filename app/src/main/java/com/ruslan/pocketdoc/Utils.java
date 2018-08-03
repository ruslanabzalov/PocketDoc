package com.ruslan.pocketdoc;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

/**
 * Класс, предоставляющий вспомогательные статические методы.
 */
public class Utils {

    /**
     * Метод формирования корректной строки специальности(-ей) врача.
     * @param specialities Список специальностей.
     * @return Строка специальности(-ей).
     */
    public static String getCorrectSpecialitiesString(@NonNull List<Speciality> specialities) {
        if (specialities.size() == 0) {
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
     * @return Строка стоимости.
     */
    public static String getCorrectPriceString(int price) {
        return (price == 0) ? "Стоимость не указана" : price + "\u20bd";
    }
}
