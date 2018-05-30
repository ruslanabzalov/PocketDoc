package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SpecialityList(
        @SerializedName("SpecList")
        @Expose
        val specialities: List<Speciality>
)