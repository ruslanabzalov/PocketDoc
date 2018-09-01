package com.ruslan.pocketdoc.data.specialities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("WeakerAccess")
@Entity(tableName = "specialities")
public class Speciality {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("Id")
    private String mId;

    @ColumnInfo(name = "name")
    @SerializedName("Name")
    private String mName;

    @Ignore
    @ColumnInfo(name = "name_genitive")
    @SerializedName("NameGenitive")
    private String mNameGenitive;

    @Ignore
    @ColumnInfo(name = "name_plural")
    @SerializedName("NamePlural")
    private String mNamePlural;

    @Ignore
    @ColumnInfo(name = "name_plural_genitive")
    @SerializedName("NamePluralGenitive")
    private String mNamePluralGenitive;

    @Ignore
    @ColumnInfo(name = "branch_name")
    @SerializedName("BranchName")
    private String mBranchName;

    public Speciality(@NonNull String id) {
        mId = id;
    }

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

    public String getNameGenitive() {
        return mNameGenitive;
    }

    public void setNameGenitive(String nameGenitive) {
        mNameGenitive = nameGenitive;
    }

    public String getNamePlural() {
        return mNamePlural;
    }

    public void setNamePlural(String namePlural) {
        mNamePlural = namePlural;
    }

    public String getNamePluralGenitive() {
        return mNamePluralGenitive;
    }

    public void setNamePluralGenitive(String namePluralGenitive) {
        mNamePluralGenitive = namePluralGenitive;
    }

    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }
}
