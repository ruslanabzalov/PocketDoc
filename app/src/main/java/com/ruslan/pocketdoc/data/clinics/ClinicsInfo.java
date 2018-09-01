package com.ruslan.pocketdoc.data.clinics;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClinicsInfo {

    @SerializedName("ClinicId")
    private int mClinicId;

    @SerializedName("Stations")
    private List<String> mStations;

    public int getClinicId() {
        return mClinicId;
    }

    public void setClinicId(int clinicId) {
        mClinicId = clinicId;
    }

    public List<String> getStations() {
        return mStations;
    }

    public void setStations(List<String> stations) {
        mStations = stations;
    }
}
