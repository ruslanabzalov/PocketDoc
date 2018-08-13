package com.ruslan.pocketdoc.data.doctors.slots;

import java.util.List;

/**
 * Класс для десериализации JSON-массива расписаний приёма.
 */
public class SlotList {

    private List<Slot> mSlots;

    public List<Slot> getSlots() {
        return mSlots;
    }

    public void setSlots(List<Slot> slots) {
        mSlots = slots;
    }
}
