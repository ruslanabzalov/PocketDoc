package com.pocketdoc.pocketdoc.doc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityList {

    @SerializedName("CityList")
    @Expose
    private List<City> mCityList;

    public List<City> getCityList() {
        return mCityList;
    }
}
