package com.ruslan.pocketdoc.data.stations;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StationList {

    @SerializedName("MetroList")
    private List<Station> mStations;

    public List<Station> getStations() {
        return mStations;
    }

    public void setStations(List<Station> stations) {
        mStations = stations;
    }
}
