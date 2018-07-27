package com.ruslan.pocketdoc;

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
    public static String getCorrectSpecialitiesString(List<Speciality> specialities) {
        if (specialities.size() == 0) {
            return "Специальность не указана";
        }
        if (specialities.size() == 1) {
            return specialities.get(0).getName();
        } else {
            StringBuilder stringBuilder = new StringBuilder(specialities.get(0).getName());
            for (int i = 1; i < specialities.size(); i++) {
                stringBuilder.append(", ").append(specialities.get(i).getName().toLowerCase());
            }
            return stringBuilder.toString();
        }
    }

    /**
     * Метод формирования корректной строки стажа работы врача.
     * @param age Стаж работы.
     * @return Строка стажа работы.
     */
    public static String getCorrectExperienceString(int age) {
        if (age == 0) {
            return "Нет опыта";
        } else if (age > 10 && age < 20) {
            return age + " лет";
        } else if (age % 10 == 1) {
            return age + " год";
        } else if (age % 10 > 1 && age % 10 < 5) {
            return age + " года";
        } else {
            return age + " лет";
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
