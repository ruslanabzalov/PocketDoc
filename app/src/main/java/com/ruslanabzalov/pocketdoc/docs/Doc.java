package com.ruslanabzalov.pocketdoc.docs;

/**
 * Класс, описывающий врача и необходимые о нём данные.
 */
public class Doc {

    private String mId;
    private String mName;
    private String mDescription;

    @Override
    public String toString() {
        return mName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
