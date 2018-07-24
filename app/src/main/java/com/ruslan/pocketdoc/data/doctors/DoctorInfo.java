package com.ruslan.pocketdoc.data.doctors;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorInfo {

    @SerializedName("Doctor")
    private List<Doctor> mDoctor;

    public List<Doctor> getDoctor() {
        return mDoctor;
    }

    public void setDoctor(List<Doctor> doctor) {
        mDoctor = doctor;
    }
}
