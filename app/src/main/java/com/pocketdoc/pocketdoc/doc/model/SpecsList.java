package com.pocketdoc.pocketdoc.doc.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecsList {

    @SerializedName("SpecList")
    private List<Spec> mSpecialities;

    public List<Spec> getSpecialities() {
        return mSpecialities;
    }

    public void setSpecialities(List<Spec> specialities) {
        mSpecialities = specialities;
    }
}
