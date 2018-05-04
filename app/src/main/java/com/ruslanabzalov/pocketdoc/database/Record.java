package com.ruslanabzalov.pocketdoc.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Record extends RealmObject {

    @PrimaryKey
    private int mId;
    @Required
    private String mUserFirstName;
    @Required
    private String mUserLastName;
    @Required
    private String mDoctorsSpeciality;
    @Required
    private String mDateOfRecord;
}
