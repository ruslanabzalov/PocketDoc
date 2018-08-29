package com.ruslan.pocketdoc.api;

import com.google.gson.annotations.SerializedName;

/**
 * Класс, описывающий тело POST-запроса для создания заявки.
 */
public class CreateRecordRequest {

    @SerializedName("name")
    private String mName;

    @SerializedName("phone")
    private String mPhone;

    @SerializedName("doctor")
    private int mDoctor;

    @SerializedName("clinic")
    private int mClinic;

    @SerializedName("slotId")
    private String mSlotId;

    @SerializedName("validate")
    private int mValidate;

    public CreateRecordRequest() {}

    public CreateRecordRequest(String name, String phone, int doctor, int clinic) {
        mName = name;
        mPhone = phone;
        mDoctor = doctor;
        mClinic = clinic;
    }

    public CreateRecordRequest(String name, String phone, int doctor,
                               int clinic, String slotId, int validate) {
        this(name, phone, doctor, clinic);
        mSlotId = slotId;
        mValidate = validate;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public int getDoctor() {
        return mDoctor;
    }

    public void setDoctor(int doctor) {
        mDoctor = doctor;
    }

    public int getClinic() {
        return mClinic;
    }

    public void setClinic(int clinic) {
        mClinic = clinic;
    }

    public String getSlotId() {
        return mSlotId;
    }

    public void setSlotId(String slotId) {
        mSlotId = slotId;
    }

    public int getValidate() {
        return mValidate;
    }

    public void setValidate(int validate) {
        mValidate = validate;
    }
}
