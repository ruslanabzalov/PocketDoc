package com.ruslan.pocketdoc;

import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

public class Utils {

    public static String getCorrectSpecialitiesString(List<Speciality> specialities) {
        if (specialities.size() == 1) {
            return specialities.get(0).getName();
        } else {
            StringBuilder stringBuilder = new StringBuilder(specialities.get(0).getName());
            for (Speciality speciality : specialities) {
                stringBuilder.append(", ").append(speciality.getName());
            }
            return stringBuilder.toString();
        }
    }

    public static String getCorrectExperienceString(int age) {
        if (age == 0) {
            return "Нет опыта";
        } else if ((age > 10 && age < 20) || (age % 10 == 0)) {
            return age + " лет";
        } else if (age == 1 || age % 10 == 1) {
            return age + " год";
        } else if ((age > 1 && age < 5) || (age % 10 > 1 && age % 10 < 5)) {
            return age + " года";
        } else {
            return age + " лет";
        }
    }

    public static String getCorrectPriceString(int price) {
        return price + "\u20bd";
    }
}
