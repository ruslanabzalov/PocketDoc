package com.ruslanabzalov.pocketdoc.disease;

import java.util.Date;
import java.util.UUID;

/**
 * Класс, описывающий заболевание.
 */
public class Disease {

    private UUID mId; // Идентификатор заболевания
    private String mTitle; // Название заболевания
    private Date mDate; // Дата начала лечения
    private boolean mCured; // Статус заболевания

    public Disease() {
        this.mId = UUID.randomUUID();
        this.mDate = new Date();
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isCured() {
        return mCured;
    }

    public void setCured(boolean cured) {
        mCured = cured;
    }
}
