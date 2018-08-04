package com.ruslan.pocketdoc.data.clinics;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Класс для десериализации JSON-массива клиник.
 */
public class ClinicList {

    @SerializedName("Total")
    private int mTotal;

    @SerializedName("ClinicList")
    private List<Clinic> mClinics;

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }

    public List<Clinic> getClinics() {
        return mClinics;
    }

    public void setClinics(List<Clinic> clinics) {
        mClinics = clinics;
    }
}
