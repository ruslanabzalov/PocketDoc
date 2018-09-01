package com.ruslan.pocketdoc.data.doctors.slots;

public class Schedule {

    private String mId;

    private String mStartTime;

    private String mFinishTime;

    Schedule(String id, String startTime, String finishTime) {
        mId = id;
        mStartTime = startTime;
        mFinishTime = finishTime;
    }

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
