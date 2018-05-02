package com.ruslanabzalov.pocketdoc.diseases;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.time.LocalDate;

/**
 * Класс, описывающий сущность "Заболевание".
 */
@Entity
class Disease {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId; // Идентификатор заболевания.
    @ColumnInfo(name = "name")
    private String mName; // Название заболевания.
    @ColumnInfo(name = "treatment_start_date")
    private LocalDate mTreatmentStartDate; // Дата начала лечения.

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setTreatmentStartDate(LocalDate treatmentStartDate) {
        mTreatmentStartDate = treatmentStartDate;
    }

    public LocalDate getTreatmentStartDate() {
        return mTreatmentStartDate;
    }
}
