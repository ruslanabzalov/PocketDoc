package com.ruslanabzalov.pocketdoc.disease;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс-синглтон, описывающий список заболеваний.
 */
public class DiseasesList {

    private static DiseasesList sDiseasesList;

    private List<Disease> mDiseases;

    /**
     * Метод, вовзращающий только один объект класса DiseasesList.
     * @param context контекст
     * @return экземпляр класса DiseaseList
     */
    public static DiseasesList get(Context context) {
        if (sDiseasesList == null) {
            sDiseasesList = new DiseasesList(context);
        }
        return sDiseasesList;
    }

    /**
     * Метод, создающий список список заболеваний.
     * @param context контекст
     */
    private DiseasesList(Context context) {
        mDiseases = new ArrayList<>();
    }

    /**
     * Метод, добавляющий новое заболевание в список.
     * @param disease заболевание
     */
    public void addDisease(Disease disease) {
        mDiseases.add(disease);
    }

    /**
     * Метод, возвращающий список заболеваний.
     * @return список заболеваний
     */
    public List<Disease> getDiseases() {
        return mDiseases;
    }

    /**
     * Метод, вовзращающий конкретное заболевание из списка.
     * @param id идентификатор заболевания
     * @return конкретное заболевание или null, если его нет в списке
     */
    public Disease getDisease(UUID id) {
        for (Disease disease : mDiseases) {
            if (disease.getId().equals(id)) {
                return disease;
            }
        }
        return null;
    }
}
