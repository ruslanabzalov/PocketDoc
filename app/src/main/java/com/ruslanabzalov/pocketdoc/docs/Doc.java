package com.ruslanabzalov.pocketdoc.docs;

import java.io.Serializable;

/**
 * Класс, описывающий врача и необходимые о нём данные.
 */
public class Doc implements Serializable {

    private String mId;
    private String mName;
    private String mDescription;
    private String mPrice;
    private String mExperience;
    private String mRating;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getExperience() {
        return mExperience;
    }

    public void setExperience(String experience) {
        mExperience = experience;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }
}
