package com.ruslanabzalov.pocketdoc.doctors;

public class Record {

    private String mDocType;
    private String mUserName;
    private String mRecordDate;


    public String getDocType() {
        return mDocType;
    }

    public void setDocType(String docType) {
        mDocType = docType;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getRecordDate() {
        return mRecordDate;
    }

    public void setRecordDate(String recordDate) {
        mRecordDate = recordDate;
    }
}
