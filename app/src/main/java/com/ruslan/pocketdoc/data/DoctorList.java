package com.ruslan.pocketdoc.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorList {

    @SerializedName("DoctorList")
    @Expose
    private List<Doctor> mDoctorList;

    public List<Doctor> getDoctorList() {
        return mDoctorList;
    }
}
