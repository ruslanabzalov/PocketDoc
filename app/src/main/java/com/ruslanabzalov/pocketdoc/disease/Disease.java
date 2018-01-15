package com.ruslanabzalov.pocketdoc.disease;

import java.util.UUID;

/**
 * Класс, описывающий заболевание.
 */
public class Disease {

    private UUID mId;
    private String mTitle;
    private DrugsList mDrugs;

    public Disease() {
        this.mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
