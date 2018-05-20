package com.ruslan.pocketdoc.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StationsList {

    @SerializedName("MetroList")
    private List<Station> mStations;

    public List<Station> getStations() {
        return mStations;
    }

    public void setStations(List<Station> stations) {
        mStations = stations;
    }
}
