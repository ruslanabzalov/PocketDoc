package com.ruslan.pocketdoc.data.doctors;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorList {

    @SerializedName("DoctorList")
    private List<Doctor> mDoctors;

    public List<Doctor> getDoctors() {
        return mDoctors;
    }
}
