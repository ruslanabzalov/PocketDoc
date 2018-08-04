package com.ruslan.pocketdoc.data.doctors.slots;

import java.util.List;

/**
 * Класс для десериализации JSON-массива расписаний приёма.
 */
public class SlotList {

    private String clinicId;

    private List<Slot> mSlots;

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public List<Slot> getSlots() {
        return mSlots;
    }

    public void setSlots(List<Slot> slots) {
        mSlots = slots;
    }
}
