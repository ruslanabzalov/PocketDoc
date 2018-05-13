package com.ruslanabzalov.pocketdoc.doctors.model;

/**
 * Класс, описывающий врача.
 */
public class Doctor {

    private String mId;
    private String mName;
    private String mDoctorPhoto;
    private String mSpecialityName;
    private String mExperience;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDoctorPhoto() {
        return mDoctorPhoto;
    }

    public void setDoctorPhoto(String doctorPhoto) {
        mDoctorPhoto = doctorPhoto;
    }

    public String getSpecialityName() {
        return mSpecialityName;
    }

    public void setSpecialityName(String specialityName) {
        mSpecialityName = specialityName;
    }

    public String getExperience() {
        return mExperience;
    }

    public void setExperience(String experience) {
        mExperience = experience;
    }
}
