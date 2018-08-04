package com.ruslan.pocketdoc.data.specialities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Класс для десериализации JSON-массива специальностей.
 */
public class SpecialityList {

    @SerializedName("SpecList")
    private List<Speciality> mSpecialities;

    public List<Speciality> getSpecialities() {
        return mSpecialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        mSpecialities = specialities;
    }
}
