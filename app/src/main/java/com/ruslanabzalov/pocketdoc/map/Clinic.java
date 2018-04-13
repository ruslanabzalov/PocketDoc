package com.ruslanabzalov.pocketdoc.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Класс, описывающий конкретную клинику.
 */
public class Clinic implements Serializable {

    @SerializedName("Id")
    @Expose
    private String mId;
    @SerializedName("Name")
    @Expose
    private String mName;
    @SerializedName("ShortName")
    @Expose
    private String mShortName;
    @SerializedName("URL")
    @Expose
    private String mUrl;
    @SerializedName("Longitude")
    @Expose
    private String mLongitude;
    @SerializedName("Latitude")
    @Expose
    private String mLatitude;
    @SerializedName("City")
    @Expose
    private String mCity;
    @SerializedName("Street")
    @Expose
    private String mStreet;
    @SerializedName("StreetId")
    @Expose
    private String mStreetId;
    @SerializedName("House")
    @Expose
    private String mHouse;
    @SerializedName("Description")
    @Expose
    private String mDescription;
    @SerializedName("ShortDescription")
    @Expose
    private String mShortDescription;
    @SerializedName("isDiagnostic")
    @Expose
    private String mIsDiagnostic;
    @SerializedName("isClinic")
    @Expose
    private String mIsClinic;
    @Expose
    private String mPhone;
    @SerializedName("PhoneAppointment")
    @Expose
    private String mPhoneAppointment;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getShortName() {
        return mShortName;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public String getCity() {
        return mCity;
    }

    public String getStreet() {
        return mStreet;
    }

    public String getStreetId() {
        return mStreetId;
    }

    public String getHouse() {
        return mHouse;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getIsDiagnostic() {
        return mIsDiagnostic;
    }

    public String getIsClinic() {
        return mIsClinic;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getPhoneAppointment() {
        return mPhoneAppointment;
    }
}
