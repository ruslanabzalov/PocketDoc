package com.ruslanabzalov.pocketdoc.illnesses;

import java.util.UUID;

/**
 * Класс, описывающий лекарство.
 */
public class Medicament {

    private UUID mDrugId;
    private String mDrugName;
    private int mNumberForUse;

    public UUID getDrugId() {
        return mDrugId;
    }

    public String getDrugName() {
        return mDrugName;
    }

    public void setDrugName(String drugName) {
        mDrugName = drugName;
    }

    public int getNumberForUse() {
        return mNumberForUse;
    }

    public void setNumberForUse(int numberForUse) {
        mNumberForUse = numberForUse;
    }
}
