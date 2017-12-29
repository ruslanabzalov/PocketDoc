package com.ruslanabzalov.pocketdoc.map;

/**
 * Класс, описывающий медицинское учреждение.
 */
public class Hospital {

    private String mHospitalName;
    private String mHospitalType;
    private String mHospitalDescription;
    private String mHospitalAddress;
    private String mHospitalPhone;

    public String getHospitalName() {
        return mHospitalName;
    }

    public void setHospitalName(String hospitalName) {
        mHospitalName = hospitalName;
    }

    public String getHospitalType() {
        return mHospitalType;
    }

    public void setHospitalType(String hospitalType) {
        mHospitalType = hospitalType;
    }

    public String getHospitalDescription() {
        return mHospitalDescription;
    }

    public void setHospitalDescription(String hospitalDescription) {
        mHospitalDescription = hospitalDescription;
    }

    public String getHospitalAddress() {
        return mHospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        mHospitalAddress = hospitalAddress;
    }

    public String getHospitalPhone() {
        return mHospitalPhone;
    }

    public void setHospitalPhone(String hospitalPhone) {
        mHospitalPhone = hospitalPhone;
    }
}
