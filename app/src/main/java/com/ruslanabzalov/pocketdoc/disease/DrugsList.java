package com.ruslanabzalov.pocketdoc.disease;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DrugsList {

    private static DrugsList sDrugsList;

    private List<Drug> mDrugs;

    public static DrugsList get(Context context) {
        if (sDrugsList == null) {
            sDrugsList = new DrugsList(context);
        }
        return sDrugsList;
    }

    private DrugsList(Context context) {
        mDrugs = new ArrayList<>();
    }

    public void addDrug(Drug drug) {
        mDrugs.add(drug);
    }

    public List<Drug> getDrugs() {
        return mDrugs;
    }

    public Drug getDrug(UUID id) {
        for (Drug drug : mDrugs) {
            if (drug.getDrugId().equals(id)) {
                return drug;
            }
        }
        return null;
    }
}
