package com.ruslan.pocketdoc.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
