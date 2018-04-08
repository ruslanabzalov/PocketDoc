package com.ruslanabzalov.pocketdoc.doctors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Doctor implements Serializable {

    @SerializedName("Id")
    @Expose
    private String mId;
    @SerializedName("Name")
    @Expose
    private String mName;
//    @SerializedName("Id")
//    @Expose
//    private String mType;
    @SerializedName("Description")
    @Expose
    private String mDescription;
//    @SerializedName("Id")
//    @Expose
//    private String mExperience;
    @SerializedName("Rating")
    @Expose
    private String mRating;
//    @SerializedName("Id")
//    @Expose
//    private String mPrice;
//    @SerializedName("Id")
//    @Expose
//    private String mDocsClinicId;
//    @SerializedName("Id")
//    @Expose
//    private String mAddress;

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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }
}
