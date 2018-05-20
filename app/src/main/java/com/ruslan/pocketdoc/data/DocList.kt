package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DocList(
        @SerializedName("DoctorList")
        @Expose
        val docList: List<Doc>
)