package com.ruslanabzalov.pocketdoc.doctors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Metro {

    @SerializedName("Id")
    @Expose
    private String mId;
    @SerializedName("Name")
    @Expose
    private String mName;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
