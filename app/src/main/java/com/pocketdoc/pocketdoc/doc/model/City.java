package com.pocketdoc.pocketdoc.doc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Класс, описывающий город.
 */
public class City {

    @SerializedName("Id")
    @Expose
    private String mId;
    @SerializedName("Name")
    @Expose
    private String mName;
    @SerializedName("Latitude")
    @Expose
    private String mLatitude; // Широта.
    @SerializedName("Longitude")
    @Expose
    private String mLongitude; // Долгота.
    @SerializedName("SearchType")
    @Expose
    private int mSearchType;
    @SerializedName("HasDiagnostic")
    @Expose
    private boolean mHasDiagnostic;
    @SerializedName("TimeZone")
    @Expose
    private int mTimeZone;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public int getSearchType() {
        return mSearchType;
    }

    public boolean isHasDiagnostic() {
        return mHasDiagnostic;
    }

    public int getTimeZone() {
        return mTimeZone;
    }
}
