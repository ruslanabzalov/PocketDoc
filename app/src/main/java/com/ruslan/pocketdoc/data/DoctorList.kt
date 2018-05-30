package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DoctorList(
        @SerializedName("DoctorList")
        @Expose
        val doctors: List<Doctor>
)