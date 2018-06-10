package com.ruslan.pocketdoc.data.stations;

import com.google.gson.annotations.SerializedName;

public class Station {

    @SerializedName("Id")
    private String mId;

    @SerializedName("Name")
    private String mName;

    @SerializedName("LineName")
    private String mLineName;

    @SerializedName("LineColor")
    private String mLineColor;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLineName() {
        return mLineName;
    }

    public void setLineName(String lineName) {
        mLineName = lineName;
    }

    public String getLineColor() {
        return mLineColor;
    }

    public void setLineColor(String lineColor) {
        mLineColor = lineColor;
    }
}
