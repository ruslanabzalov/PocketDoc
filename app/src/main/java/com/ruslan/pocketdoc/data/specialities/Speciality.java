package com.ruslan.pocketdoc.data.specialities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "speciality")
public class Speciality {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("Id")
    private String mId;

    @ColumnInfo(name = "name")
    @SerializedName("Name")
    private String mName;

    @ColumnInfo(name = "branch_name")
    @SerializedName("BranchName")
    private String mBranchName;

    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(@NonNull String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }
}
