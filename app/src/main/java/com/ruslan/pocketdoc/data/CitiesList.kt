package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CitiesList(
        @SerializedName("CityList")
        @Expose
        val citiesList: List<City>
)