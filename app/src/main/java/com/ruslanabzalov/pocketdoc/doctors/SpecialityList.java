package com.ruslanabzalov.pocketdoc.doctors;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecialityList {

    @SerializedName("SpecList")
    private List<Speciality> mSpecialities;

    public List<Speciality> getSpecialities() {
        return mSpecialities;
    }
}