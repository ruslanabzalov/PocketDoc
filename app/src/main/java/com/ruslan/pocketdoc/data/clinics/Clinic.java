package com.ruslan.pocketdoc.data.clinics;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Класс, описывающий сущность "Клиника".
 */
@SuppressWarnings("WeakerAccess")
@Entity(tableName = "clinics")
public class Clinic {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("Id")
    private int mId;

    @ColumnInfo(name = "name")
    @SerializedName("Name")
    private String mName;

    @ColumnInfo(name = "short_name")
    @SerializedName("ShortName")
    private String mShortName;

    @ColumnInfo(name = "url")
    @SerializedName("URL")
    private String mUrl;

    @ColumnInfo(name = "longitude")
    @SerializedName("Longitude")
    private String mLongitude;

    @ColumnInfo(name = "latitude")
    @SerializedName("Latitude")
    private String mLatitude;

    @ColumnInfo(name = "street")
    @SerializedName("Street")
    private String mStreet;

    @ColumnInfo(name = "house")
    @SerializedName("House")
    private String mHouse;

    @ColumnInfo(name = "description")
    @SerializedName("Description")
    private String mDescription;

    @ColumnInfo(name = "short_description")
    @SerializedName("ShortDescription")
    private String mShortDescription;

    @ColumnInfo(name = "is_diagnostic")
    @SerializedName("IsDiagnostic")
    private String mIsDiagnostic;

    @ColumnInfo(name = "is_clinic")
    @SerializedName("isClinic")
    private String mIsClinic;

    @ColumnInfo(name = "phone")
    @SerializedName("Phone")
    private String mPhone;

    @ColumnInfo(name = "phone_appointment")
    @SerializedName("PhoneAppointment")
    private String mPhoneAppointment;

    @ColumnInfo(name = "min_price")
    @SerializedName("MinPrice")
    private String mMinPrice;

    @ColumnInfo(name = "max_price")
    @SerializedName("MaxPrice")
    private String mMaxPrice;

    @ColumnInfo(name = "logo")
    @SerializedName("Logo")
    private String mLogo;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getShortName() {
        return mShortName;
    }

    public void setShortName(String shortName) {
        mShortName = shortName;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getStreet() {
        return mStreet;
    }

    public void setStreet(String street) {
        mStreet = street;
    }

    public String getHouse() {
        return mHouse;
    }

    public void setHouse(String house) {
        mHouse = house;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }

    public String getIsDiagnostic() {
        return mIsDiagnostic;
    }

    public void setIsDiagnostic(String isDiagnostic) {
        mIsDiagnostic = isDiagnostic;
    }

    public String getIsClinic() {
        return mIsClinic;
    }

    public void setIsClinic(String isClinic) {
        mIsClinic = isClinic;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPhoneAppointment() {
        return mPhoneAppointment;
    }

    public void setPhoneAppointment(String phoneAppointment) {
        mPhoneAppointment = phoneAppointment;
    }

    public String getMinPrice() {
        return mMinPrice;
    }

    public void setMinPrice(String minPrice) {
        mMinPrice = minPrice;
    }

    public String getMaxPrice() {
        return mMaxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        mMaxPrice = maxPrice;
    }

    public String getLogo() {
        return mLogo;
    }

    public void setLogo(String logo) {
        mLogo = logo;
    }
}
