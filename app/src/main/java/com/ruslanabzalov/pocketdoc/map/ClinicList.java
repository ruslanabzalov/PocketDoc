package com.ruslanabzalov.pocketdoc.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Класс, описывающий список клиник.
 */
public class ClinicList {

    @SerializedName("Total")
    @Expose
    private String mTotalClinicsNumber;
    @SerializedName("ClinicList")
    @Expose
    private List<Clinic> mClinics;

    public String getTotalClinicsNumber() {
        return mTotalClinicsNumber;
    }

    public List<Clinic> getClinics() {
        return mClinics;
    }
}
