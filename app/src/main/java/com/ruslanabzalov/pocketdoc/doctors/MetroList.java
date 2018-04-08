package com.ruslanabzalov.pocketdoc.doctors;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MetroList {

    @SerializedName("MetroList")
    private List<Metro> mMetros;

    public List<Metro> getMetroList() {
        return mMetros;
    }
}
