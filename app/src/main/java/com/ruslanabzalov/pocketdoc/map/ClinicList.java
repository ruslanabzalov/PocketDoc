package com.ruslanabzalov.pocketdoc.map;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClinicList {

    @SerializedName("ClinicList")
    private List<Clinic> mClinics;

    public List<Clinic> getClinics() {
        return mClinics;
    }
}
