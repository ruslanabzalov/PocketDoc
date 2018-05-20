package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SpecList(
        @SerializedName("SpecList")
        @Expose
        val specList: List<Spec>
)