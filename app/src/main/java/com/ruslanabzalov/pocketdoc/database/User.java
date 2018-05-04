package com.ruslanabzalov.pocketdoc.database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class User extends RealmObject {

    @PrimaryKey
    private int mId;
    @Required
    private String mFirstName;
    private String mSecondName;
    private int mAge;
    private RealmList<Record> mRecords;

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getSecondName() {
        return mSecondName;
    }

    public void setSecondName(String secondName) {
        mSecondName = secondName;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }
}
