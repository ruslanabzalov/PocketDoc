package com.ruslanabzalov.pocketdoc.docs;

import java.io.Serializable;

/**
 * Класс, описывающий врача и необходимые о нём данные.
 */
public class Doc implements Serializable {

    private String mId;
    private String mName;
    private String mDescription;
    private String mExperience;
    private String mRating;
    private String mPrice;
    private String mDocsClinicId;
    private String mClinicName;
    private String mClinicAddress;
    private String mClinicDescription;

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

    public String getClinicName() {
        return mClinicName;
    }

    public void setClinicName(String clinicName) {
        mClinicName = clinicName;
    }

    public String getClinicAddress() {
        return mClinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        mClinicAddress = clinicAddress;
    }

    public String getClinicDescription() {
        return mClinicDescription;
    }

    public void setClinicDescription(String clinicDescription) {
        mClinicDescription = clinicDescription;
    }
}
