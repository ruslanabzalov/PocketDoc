package com.ruslan.pocketdoc.data.doctors.slots;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Класс для сериализации JSON-массива расписаний приёма.
 */
public class SlotList {

    @SerializedName("SlotList")
    private List<Slot> mSlots;

    public List<Slot> getSlots() {
        return mSlots;
    }

    public void setSlots(List<Slot> slots) {
        mSlots = slots;
    }
}