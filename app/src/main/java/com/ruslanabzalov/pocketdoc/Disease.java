package com.ruslanabzalov.pocketdoc;

import java.util.Date;
import java.util.UUID;

/**
 * Класс, описывающий заболевание.
 */
class Disease {

    private UUID mId; // Генерируемый идентификатор заболевания
    private String mTitle; // Название заболевания
    private Date mDate; // Дата заболевания
    private boolean mCured; // Статус заболевания

    Disease() {
        this.mId = UUID.randomUUID();
        this.mDate = new Date();
    }

    UUID getId() {
        return mId;
    }

    String getTitle() {
        return mTitle;
    }

    void setTitle(String title) {
        mTitle = title;
    }

    Date getDate() {
        return mDate;
    }

    void setDate(Date date) {
        mDate = date;
    }

    boolean isCured() {
        return mCured;
    }

    void setCured(boolean cured) {
        mCured = cured;
    }
}
