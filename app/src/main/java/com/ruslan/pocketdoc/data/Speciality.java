package com.ruslan.pocketdoc.data;

import com.google.gson.annotations.SerializedName;

public class Speciality {

    @SerializedName("Id")
    private String mId;

    @SerializedName("Name")
    private String mName;

    @SerializedName("BranchName")
    private String mBranchName;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getBranchName() {
        return mBranchName;
    }
}
