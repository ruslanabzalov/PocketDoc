package com.ruslanabzalov.pocketdoc.doctors;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StationsList {

    @SerializedName("MetroList")
    private List<Station> mStations;

    public void setStations(List<Station> stations) {
        mStations = stations;
    }

    public List<Station> getStations() {
        return mStations;
    }
}
