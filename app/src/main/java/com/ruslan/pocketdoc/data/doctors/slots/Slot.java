package com.ruslan.pocketdoc.data.doctors.slots;

/**
 * Класс, описывающий расписание приёма.
 */
public class Slot {

    private String mId;

    private String mStartTime;

    private String mFinishTime;

    Slot(String id, String startTime, String finishTime) {
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
