package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CityList(
        @SerializedName("CityList")
        @Expose
        val cityList: List<City>
)