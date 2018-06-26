package com.ruslan.pocketdoc.data.clinics;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClinicList {

    @SerializedName("ClinicList")
    private List<Clinic> mClinics;

    public List<Clinic> getClinics() {
        return mClinics;
    }

    public void setClinics(List<Clinic> clinics) {
        mClinics = clinics;
    }
}
