package com.ruslanabzalov.pocketdoc.doctors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Station {

    @SerializedName("Id")
    @Expose
    private String mId;
    @SerializedName("Name")
    @Expose
    private String mName;

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
}
