package com.ruslan.pocketdoc.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DoctorList implements Serializable {

    @SerializedName("DoctorList")
    private List<Doctor> mDoctors;

    public List<Doctor> getDoctors() {
        return mDoctors;
    }
}
