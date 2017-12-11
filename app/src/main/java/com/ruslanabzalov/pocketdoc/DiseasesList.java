package com.ruslanabzalov.pocketdoc;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс-синглтон для отображения списка заболеваний.
 */
class DiseasesList {

    private static DiseasesList sDiseasesList;

    private List<Disease> mDiseases;

    /**
     * Метод, вовзращающий только один объект класса DiseasesList.
     * @param context
     * @return
     */
    static DiseasesList get(Context context) {
        if (sDiseasesList == null) {
            sDiseasesList = new DiseasesList(context);
        }
        return sDiseasesList;
    }

    /**
     * Метод, создающий список список заболеваний.
     * @param context
     */
    private DiseasesList(Context context) {
        mDiseases = new ArrayList<>();
    }

    /**
     * Метод, добавляющий новое заболевание в список.
     * @param disease
     */
    void addDisease(Disease disease) {
        mDiseases.add(disease);
    }

    /**
     * Метод, возвращающий список заболеваний.
     * @return
     */
    List<Disease> getDiseases() {
        return mDiseases;
    }

    /**
     * Метод, вовзращающий конкретное заболевание из списка.
     * @param id
     * @return
     */
    Disease getDisease(UUID id) {
        for (Disease disease : mDiseases) {
            if (disease.getId().equals(id)) {
                return disease;
            }
        }
        return null;
    }
}
