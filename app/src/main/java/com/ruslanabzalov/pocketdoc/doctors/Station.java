package com.ruslanabzalov.pocketdoc.doctors;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Класс, описывающий конкретную станцию метро.
 */
public class Station {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("Id")
    @Expose
    private String mId;

    @ColumnInfo(name = "name")
    @SerializedName("Name")
    @Expose
    private String mName;

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
}
