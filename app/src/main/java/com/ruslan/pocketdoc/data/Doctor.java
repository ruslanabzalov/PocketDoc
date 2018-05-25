package com.ruslan.pocketdoc.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Doctor implements Serializable {

    @SerializedName("Id")
    @Expose
    private int mId;

    @SerializedName("Name")
    @Expose
    private String mName;

    @SerializedName("Rating")
    @Expose
    private String mRating;

    @SerializedName("Sex")
    @Expose
    private int mSex;

    @SerializedName("Img")
    @Expose
    private String mPhotoUrl;

    @SerializedName("Description")
    @Expose
    private String mDescription;

    @SerializedName("ExperienceYear")
    @Expose
    private int mExperience;

    @SerializedName("Price")
    @Expose
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
