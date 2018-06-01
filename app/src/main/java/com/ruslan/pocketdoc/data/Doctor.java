package com.ruslan.pocketdoc.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Doctor implements Serializable {

    @SerializedName("Id")
    private int mId;

    @SerializedName("Name")
    private String mName;

    @SerializedName("Rating")
    private String mRating;

    @SerializedName("Sex")
    private int mSex;

    @SerializedName("Img")
    private String mPhotoUrl;

    @SerializedName("Description")
    private String mDescription;

    @SerializedName("ExperienceYear")
    private int mExperience;

    @SerializedName("Price")
    private int mPrice;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getRating() {
        return mRating;
    }

    public int getSex() {
        return mSex;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getExperience() {
        return mExperience;
    }

    public int getPrice() {
        return mPrice;
    }
}
