package abzalov.ruslan.pocketdoc.api;

import com.google.gson.annotations.SerializedName;

public class CreateRecordResponse {

    @SerializedName("status")
    private String mStatus;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("id")
    private int mId;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
