package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StationList(
        @SerializedName("MetroList")
        @Expose
        val stationList: List<Station>
)