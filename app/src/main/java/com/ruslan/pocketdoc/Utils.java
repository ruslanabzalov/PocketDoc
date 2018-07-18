package com.ruslan.pocketdoc;

public class Utils {

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
}
