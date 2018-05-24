package com.ruslan.pocketdoc.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecialityList {

    @SerializedName("SpecList")
    @Expose
    private List<Speciality> mSpecialityList;

    public List<Speciality> getSpecialityList() {
        return mSpecialityList;
    }
}
