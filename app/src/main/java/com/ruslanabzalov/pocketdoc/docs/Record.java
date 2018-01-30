package com.ruslanabzalov.pocketdoc.docs;

/**
 * Класс, описывающий одну конкретную запись к врачу.
 */
public class Record {

    private String mDocName;
    private String mDocType;
    private String mDocAddress;
    private String mDocDescription;
    private String mUserName;
    private String mUserPhone;
    private String mRecordDate;

    public String getDocName() {
        return mDocName;
    }

    public void setDocName(String docName) {
        mDocName = docName;
    }

    public String getDocType() {
        return mDocType;
    }

    public void setDocType(String docType) {
        mDocType = docType;
    }

    public String getDocAddress() {
        return mDocAddress;
    }

    public void setDocAddress(String docAddress) {
        mDocAddress = docAddress;
    }

    public String getDocDescription() {
        return mDocDescription;
    }

    public void setDocDescription(String docDescription) {
        mDocDescription = docDescription;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUserPhone() {
        return mUserPhone;
    }

    public void setUserPhone(String userPhone) {
        mUserPhone = userPhone;
    }

    public String getRecordDate() {
        return mRecordDate;
    }

    public void setRecordDate(String recordDate) {
        mRecordDate = recordDate;
    }
}
