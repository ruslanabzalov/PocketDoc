package com.ruslanabzalov.pocketdoc.docs;

import java.io.Serializable;

public class Doc implements Serializable {

    private String mId;
    private String mName;
    private String mType;
    private String mDescription;
    private String mExperience;
    private String mRating;
    private String mPrice;
    private String mDocsClinicId;
    private String mAddress;

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

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
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

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getDocsClinicId() {
        return mDocsClinicId;
    }

    public void setDocsClinicId(String docsClinicId) {
        mDocsClinicId = docsClinicId;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }
}
