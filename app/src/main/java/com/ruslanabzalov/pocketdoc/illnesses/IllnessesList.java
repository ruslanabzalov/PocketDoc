package com.ruslanabzalov.pocketdoc.illnesses;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class IllnessesList {

    private static IllnessesList sIllnessesList;

    private List<Illness> mIllnesses;

    static IllnessesList get(Context context) {
        if (sIllnessesList == null) {
            sIllnessesList = new IllnessesList(context);
        }
        return sIllnessesList;
    }

    private IllnessesList(Context context) {
        mIllnesses = new ArrayList<>();
    }

    void addDisease(Illness illness) {
        mIllnesses.add(illness);
    }

    List<Illness> getIllnesses() {
        return mIllnesses;
    }

    Illness getDisease(UUID id) {
        for (Illness illness : mIllnesses) {
            if (illness.getId().equals(id)) {
                return illness;
            }
        }
        return null;
    }
}
