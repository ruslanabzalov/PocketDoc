package com.ruslanabzalov.pocketdoc;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс-синглтон для отображения списка заболеваний.
 * */
class DiseasesList {

    private static DiseasesList sDiseasesList;

    private List<Disease> mDiseases;

    /**
     * Метод, вовзращающий лишь один объект класса DiseasesList.
     * */
    static DiseasesList get(Context context) {
        if (sDiseasesList == null) {
            sDiseasesList = new DiseasesList(context);
        }
        return sDiseasesList;
    }

    private DiseasesList(Context context) {
        mDiseases = new ArrayList<>();
        // Временное создание списка из 100 заболеваний
        for (int counter = 0; counter < 100; counter++) {
            Disease disease = new Disease();
            disease.setTitle("Disease #" + counter);
            disease.setCured(counter % 2 == 0); // Для каждого второго заболевания
            mDiseases.add(disease);
        }
    }

    /**
     * Метод, добавляющий новое заболевание в список.
     * */
    void addDisease(Disease disease) {
        mDiseases.add(disease);
    }

    /**
     * Метод, возвращающий список заболеваний.
     * */
    List<Disease> getDiseases() {
        return mDiseases;
    }

    /**
     * Метод, вовзращающий конкретное заболевание из списка.
     * */
    Disease getDisease(UUID id) {
        for (Disease disease : mDiseases) {
            if (disease.getId().equals(id)) {
                return disease;
            }
        }
        return null;
    }
}
