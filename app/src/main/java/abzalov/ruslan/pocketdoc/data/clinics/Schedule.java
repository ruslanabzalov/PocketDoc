package abzalov.ruslan.pocketdoc.data.clinics;

import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("Day")
    private String mDay;

    @SerializedName("StartTime")
    private String mStartTime;

    @SerializedName("EndTime")
    private String mEndTime;

    @SerializedName("DayTitle")
    private String mDayTitle;

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getDayTitle() {
        return mDayTitle;
    }

    public void setDayTitle(String dayTitle) {
        mDayTitle = dayTitle;
    }
}
