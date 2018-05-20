package com.ruslan.pocketdoc.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

public class Record {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "doctor_name")
    private String mDoctorName;
    @ColumnInfo(name = "doctor_type")
    private String mDoctorType;
    @ColumnInfo(name = "doctor_speciality")
    private String mDoctorSpeciality;
    @ColumnInfo(name = "record_date")
    private String mRecordDate;

    public int getId() {
        return mId;
    }

    public String getDoctorName() {
        return mDoctorName;
    }

    public void setDoctorName(String doctorName) {
        mDoctorName = doctorName;
    }

    public String getDoctorType() {
        return mDoctorType;
    }

    public void setDoctorType(String doctorType) {
        mDoctorType = doctorType;
    }

    public String getDoctorSpeciality() {
        return mDoctorSpeciality;
    }

    public void setDoctorSpeciality(String doctorSpeciality) {
        mDoctorSpeciality = doctorSpeciality;
    }

    public String getRecordDate() {
        return mRecordDate;
    }

    public void setRecordDate(String recordDate) {
        mRecordDate = recordDate;
    }
}
