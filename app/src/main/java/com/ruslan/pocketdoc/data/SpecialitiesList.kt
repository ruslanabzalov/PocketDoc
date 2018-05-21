package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SpecialitiesList(
        @SerializedName("SpecList")
        @Expose
        val specialitiesList: List<Speciality>
)