package com.pocketdoc.pocketdoc.doc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocList {

    @SerializedName("DoctorList")
    @Expose
    private List<Doc> mDocList;

    public List<Doc> getDocList() {
        return mDocList;
    }
}
