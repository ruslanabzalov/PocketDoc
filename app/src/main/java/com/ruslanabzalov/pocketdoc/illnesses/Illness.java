package com.ruslanabzalov.pocketdoc.illnesses;

import java.util.UUID;

/**
 * Класс, описывающий заболевание.
 */
class Illness {

    private UUID mId;
    private String mTitle;

    Illness() {
        this.mId = UUID.randomUUID();
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
}
