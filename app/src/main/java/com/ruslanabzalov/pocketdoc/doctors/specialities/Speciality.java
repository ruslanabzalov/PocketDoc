package com.ruslanabzalov.pocketdoc.doctors.specialities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Класс, описывающий конкретную специальность врачей.
 */
public class Speciality {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("Id")
    @Expose
    private String mId;

    @ColumnInfo(name = "name")
    @SerializedName("Name")
    @Expose
    private String mName;

    @ColumnInfo(name = "branch_name")
    @SerializedName("BranchName")
    @Expose
    private String mBranchName;

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }

    public String getBranchName() {
        return mBranchName;
    }
}
