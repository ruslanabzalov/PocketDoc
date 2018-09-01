package com.ruslan.pocketdoc.api;

import com.google.gson.annotations.SerializedName;

public class CreateRecordRequestSimple implements Requestable {

    @SerializedName("name")
    private String mName;

    @SerializedName("doctor")
    private String mPhone;

    @SerializedName("doctor")
    private int mDoctorId;

    @SerializedName("clinic")
    private int mClinicId;

    public CreateRecordRequestSimple(String name, String phone, int doctorId, int clinicId) {
        mName = name;
        mPhone = phone;
        mDoctorId = doctorId;
        mClinicId = clinicId;
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public int getDoctorId() {
        return mDoctorId;
    }

    public int getClinicId() {
        return mClinicId;
    }
}
