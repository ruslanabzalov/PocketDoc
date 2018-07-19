package com.ruslan.pocketdoc.data.doctors.slots;

import com.google.gson.annotations.SerializedName;

class Slot {

    @SerializedName("Id")
    private String mId;

    @SerializedName("StartTime")
    private String mStartTime;

    @SerializedName("FinishTime")
    private String mFinishTime;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getFinishTime() {
        return mFinishTime;
    }

    public void setFinishTime(String finishTime) {
        mFinishTime = finishTime;
    }
}
