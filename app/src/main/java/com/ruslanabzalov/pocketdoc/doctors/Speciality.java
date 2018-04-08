package com.ruslanabzalov.pocketdoc.doctors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Класс, описывающий конкретную специальность врачей.
 */
public class Speciality {

    @SerializedName("Id")
    @Expose
    private String mId;
    @SerializedName("Name")
    @Expose
    private String mName;
    @SerializedName("BranchName")
    @Expose
    private String mBranchName;

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }

    public String getBranchName() {
        return mBranchName;
    }
}
