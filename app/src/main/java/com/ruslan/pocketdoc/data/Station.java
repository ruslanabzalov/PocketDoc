package com.ruslan.pocketdoc.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Station {

    @SerializedName("Id")
    @Expose
    private String mId;

    @SerializedName("Name")
    @Expose
    private String mName;

    @SerializedName("LineName")
    @Expose
    private String mLineName;

    @SerializedName("LineColor")
    @Expose
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
