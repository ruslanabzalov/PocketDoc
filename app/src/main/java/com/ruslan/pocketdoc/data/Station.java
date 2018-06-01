package com.ruslan.pocketdoc.data;

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

    public String getName() {
        return mName;
    }

    public String getLineName() {
        return mLineName;
    }

    public String getLineColor() {
        return mLineColor;
    }
}
