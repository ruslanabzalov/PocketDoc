package com.ruslan.pocketdoc.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StationList {

    @SerializedName("MetroList")
    @Expose
    private List<Station> mStationList;

    public List<Station> getStationList() {
        return mStationList;
    }
}
