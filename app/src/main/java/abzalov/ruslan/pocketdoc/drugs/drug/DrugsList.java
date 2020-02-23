package abzalov.ruslan.pocketdoc.drugs.drug;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс, описывающий список лекарств.
 */
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

    public void addMedicament(Drug drug) {
        mDrugs.add(drug);
    }

    public List<Drug> getMedicaments() {
        return mDrugs;
    }

    public Drug getMedicament(UUID id) {
        for (Drug drug : mDrugs) {
            if (drug.getId().equals(id)) {
                return drug;
            }
        }
        return null;
    }
}
