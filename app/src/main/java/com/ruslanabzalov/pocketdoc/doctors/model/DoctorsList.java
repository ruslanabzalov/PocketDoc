package com.ruslanabzalov.pocketdoc.doctors.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorsList {

    @SerializedName("DoctorList")
    @Expose
    private List<Doctor> mDoctorsList;

    public List<Doctor> getDoctorsList() {
        return mDoctorsList;
    }

    public void setDoctorsList(List<Doctor> doctorsList) {
        mDoctorsList = doctorsList;
    }
}
