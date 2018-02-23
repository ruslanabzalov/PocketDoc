package com.ruslanabzalov.pocketdoc.treatment;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MedicamentsList {

    private static MedicamentsList sMedicamentsList;

    private List<Medicament> mMedicaments;

    static MedicamentsList get(Context context) {
        if (sMedicamentsList == null) {
            sMedicamentsList = new MedicamentsList(context);
        }
        return sMedicamentsList;
    }

    private MedicamentsList(Context context) {
        mMedicaments = new ArrayList<>();
    }

    void addMedicament(Medicament medicament) {
        mMedicaments.add(medicament);
    }

    List<Medicament> getMedicaments() {
        return mMedicaments;
    }

    Medicament getMedicament(UUID id) {
        for (Medicament medicament : mMedicaments) {
            if (medicament.getId().equals(id)) {
                return medicament;
            }
        }
        return null;
    }
}
