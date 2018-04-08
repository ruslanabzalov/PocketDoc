package com.ruslanabzalov.pocketdoc.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class Clinic extends RealmObject implements Serializable {

    @SerializedName("Id")
    @Expose
    private String mId;
    @SerializedName("Name")
    @Expose
    private String mName;
//    private String mType;
    @SerializedName("Description")
    @Expose
    private String mDescription;
//    private String mAddress;
//    private String mPhone;
//    private String mUrl;
//    private String mLongitude;
//    private String mLatitude;

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

//    public String getType() {
//        return mType;
//    }
//
//    public void setType(String type) {
//        mType = type;
//    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
//
//    public String getAddress() {
//        return mAddress;
//    }
//
//    public void setAddress(String address) {
//        mAddress = address;
//    }
//
//    public String getPhone() {
//        return mPhone;
//    }
//
//    public void setPhone(String phone) {
//        mPhone = phone;
//    }
//
//    public String getUrl() {
//        return mUrl;
//    }
//
//    public void setUrl(String url) {
//        mUrl = url;
//    }
//
//    public String getLongitude() {
//        return mLongitude;
//    }
//
//    public void setLongitude(String longitude) {
//        mLongitude = longitude;
//    }
//
//    public String getLatitude() {
//        return mLatitude;
//    }
//
//    public void setLatitude(String latitude) {
//        mLatitude = latitude;
//    }
}
