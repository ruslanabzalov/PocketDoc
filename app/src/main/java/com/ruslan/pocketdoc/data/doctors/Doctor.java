package com.ruslan.pocketdoc.data.doctors;

import com.google.gson.annotations.SerializedName;
import com.ruslan.pocketdoc.data.clinics.ClinicsInfo;
import com.ruslan.pocketdoc.data.doctors.slots.Schedule;
import com.ruslan.pocketdoc.data.doctors.slots.SlotList;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

public class Doctor {

    @SerializedName("Id")
    private int mId;

    @SerializedName("Name")
    private String mName;

    @SerializedName("Rating")
    private String mRating;

    @SerializedName("Sex")
    private int mSex;

    @SerializedName("Img")
    private String mPhotoUrl;

    @SerializedName("Description")
    private String mDescription;

    @SerializedName("ExperienceYear")
    private int mExperience;

    @SerializedName("Price")
    private int mPrice;

    @SerializedName("Clinics")
    private List<Integer> mClinicsIds;

    @SerializedName("Specialities")
    private List<Speciality> mSpecialities;

    @SerializedName("Slots")
    private SlotList mSlotList;

    @SerializedName("ClinicsInfo")
    private List<ClinicsInfo> mClinicsInfos;

    private List<Schedule> mDaySchedules;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public int getSex() {
        return mSex;
    }

    public void setSex(int sex) {
        mSex = sex;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getExperience() {
        return mExperience;
    }

    public void setExperience(int experience) {
        mExperience = experience;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public List<Integer> getClinicsIds() {
        return mClinicsIds;
    }

    public void setClinicsIds(List<Integer> clinicsIds) {
        mClinicsIds = clinicsIds;
    }

    public List<Speciality> getSpecialities() {
        return mSpecialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        mSpecialities = specialities;
    }

    public SlotList getSlotList() {
        return mSlotList;
    }

    public void setSlotList(SlotList slotList) {
        mSlotList = slotList;
    }

    public List<ClinicsInfo> getClinicsInfos() {
        return mClinicsInfos;
    }

    public void setClinicsInfos(List<ClinicsInfo> clinicsInfos) {
        mClinicsInfos = clinicsInfos;
    }

    public List<Schedule> getDaySchedules() {
        return mDaySchedules;
    }

    public void setDaySchedules(List<Schedule> daySchedules) {
        mDaySchedules = daySchedules;
    }
}
