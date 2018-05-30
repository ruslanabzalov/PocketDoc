package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StationList(
        @SerializedName("MetroList")
        @Expose
        val stations: List<Station>
)