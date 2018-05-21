package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StationsList(
        @SerializedName("MetroList")
        @Expose
        val stationsList: List<Station>
)