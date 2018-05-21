package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DoctorsList(
        @SerializedName("DoctorList")
        @Expose
        val doctorsList: List<Doctor>
)