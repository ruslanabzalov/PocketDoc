package com.ruslanabzalov.pocketdoc.doctors;

/**
 * Класс, описывающий врача.
 */
public class Doctor {

    private String mName;
    private String mSpeciality;
    private String mExperience;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSpeciality() {
        return mSpeciality;
    }

    public void setSpeciality(String speciality) {
        mSpeciality = speciality;
    }

    public String getExperience() {
        return mExperience;
    }

    public void setExperience(String experience) {
        mExperience = experience;
    }
}
