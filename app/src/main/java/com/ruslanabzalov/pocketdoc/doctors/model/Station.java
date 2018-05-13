package com.ruslanabzalov.pocketdoc.doctors.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Класс, описывающий конкретную станцию метро.
 */
public class Station {

    @Expose
    @SerializedName("Id")
    private String mId;
    @Expose
    @SerializedName("Name")
    private String mName;
    @Expose
    @SerializedName("LineName")
    private String mLineName;
    @Expose
    @SerializedName("LineColor")
    private String mLineColor;
    @Expose
    @SerializedName("DistrictIds")
    private List<String> mDistricts;

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

    public List<String> getDistricts() {
        return mDistricts;
    }

    public void setDistricts(List<String> districts) {
        mDistricts = districts;
    }
}
