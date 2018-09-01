package com.ruslan.pocketdoc.api;

import com.google.gson.annotations.SerializedName;

public class CreateRecordRequestSchedule implements Requestable {

    @SerializedName("name")
    private String mName;

    @SerializedName("phone")
    private String mPhone;

    @SerializedName("doctor")
    private int mDoctorId;

    @SerializedName("clinic")
    private int mClinicId;

    @SerializedName("slotId")
    private String mSlotId;

    @SerializedName("validate")
    private int mValidate;

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

    public String getSlotId() {
        return mSlotId;
    }

    public int getValidate() {
        return mValidate;
    }

    private CreateRecordRequestSchedule(Builder builder) {
        mName = builder.mName;
        mPhone = builder.mPhone;
        mDoctorId = builder.mDoctorId;
        mClinicId = builder.mClinicId;
        mSlotId = builder.mSlotId;
        mValidate = builder.mValidate;
    }

    public static class Builder {

        private String mName;

        private String mPhone;

        private int mDoctorId;

        private int mClinicId;

        private String mSlotId;

        private int mValidate;

        public Builder name(String name) {
            mName = name;
            return this;
        }

        public Builder phone(String phone) {
            mPhone = phone;
            return this;
        }

        public Builder doctor(int doctorId) {
            mDoctorId = doctorId;
            return this;
        }

        public Builder clinic(int clinicId) {
            mClinicId = clinicId;
            return this;
        }

        public Builder slot(String slotId) {
            mSlotId = slotId;
            return this;
        }

        public Builder validate(int validateOption) {
            mValidate = validateOption;
            return this;
        }

        public CreateRecordRequestSchedule build() {
            return new CreateRecordRequestSchedule(this);
        }
    }
}
