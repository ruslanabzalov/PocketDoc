package com.ruslan.pocketdoc.data;

public class User {

    private String mName;
    private String mPhoneNumber;

    public User(String name, String phoneNumber) {
        mName = name;
        mPhoneNumber = phoneNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }
}
