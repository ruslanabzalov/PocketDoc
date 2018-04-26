package com.ruslanabzalov.pocketdoc.user;

/**
 * Класс, описывающий пользователя.
 */
public class User {

    private String mFirstName;
    private String mLastName;
    private int mAge;

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public int getAge() {
        return mAge;
    }
}
