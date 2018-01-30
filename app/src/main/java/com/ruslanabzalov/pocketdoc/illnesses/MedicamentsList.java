package com.ruslanabzalov.pocketdoc.illnesses;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MedicamentsList {

    private static MedicamentsList sMedicamentsList;

    private List<Medicament> mMedicaments;

    public static MedicamentsList get(Context context) {
        if (sMedicamentsList == null) {
            sMedicamentsList = new MedicamentsList(context);
        }
        return sMedicamentsList;
    }

    private MedicamentsList(Context context) {
        mMedicaments = new ArrayList<>();
    }

    public void addDrug(Medicament medicament) {
        mMedicaments.add(medicament);
    }

    public List<Medicament> getMedicaments() {
        return mMedicaments;
    }

    public Medicament getDrug(UUID id) {
        for (Medicament medicament : mMedicaments) {
            if (medicament.getDrugId().equals(id)) {
                return medicament;
            }
        }
        return null;
    }
}
