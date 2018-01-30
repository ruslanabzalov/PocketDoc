package com.ruslanabzalov.pocketdoc.illnesses;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс-синглтон, описывающий список заболеваний.
 */
class IllnessesList {

    private static IllnessesList sIllnessesList;

    private List<Illness> mIllnesses;

    /**
     * Метод, возвращающий только единственный объект класса IllnessesList.
     * @param context контекст
     * @return экземпляр класса DiseaseList
     */
    static IllnessesList get(Context context) {
        if (sIllnessesList == null) {
            sIllnessesList = new IllnessesList(context);
        }
        return sIllnessesList;
    }

    /**
     * Закрытый конструктор класса IllnessesList.
     * @param context контекст
     */
    private IllnessesList(Context context) {
        mIllnesses = new ArrayList<>();
    }

    /**
     * Метод, добавляющий новое заболевание в список.
     * @param illness заболевание
     */
    void addDisease(Illness illness) {
        mIllnesses.add(illness);
    }

    /**
     * Геттер, возвращающий список заболеваний.
     * @return список заболеваний
     */
    List<Illness> getIllnesses() {
        return mIllnesses;
    }

    /**
     * Метод, вовзращающий конкретное заболевание из списка по его идентификатору.
     * @param id идентификатор заболевания
     * @return конкретное заболевание или null, если его нет в списке
     */
    Illness getDisease(UUID id) {
        for (Illness illness : mIllnesses) {
            if (illness.getId().equals(id)) {
                return illness;
            }
        }
        return null;
    }
}
