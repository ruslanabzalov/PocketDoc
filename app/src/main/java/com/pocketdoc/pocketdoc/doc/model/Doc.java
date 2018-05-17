package com.pocketdoc.pocketdoc.doc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Класс, описывающий врача.
 */
public class Doc {

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
    private String mPhoto;
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

    public String getPhoto() {
        return mPhoto;
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
