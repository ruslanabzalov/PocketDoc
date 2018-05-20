package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Station(
        @SerializedName("Id")
        @Expose
        val id: String,

        @SerializedName("Name")
        @Expose
        val name: String,

        @SerializedName("LineName")
        @Expose
        val lineName: String,

        @SerializedName("LineColor")
        @Expose
        val lineColor: String,

        @SerializedName("DistrictIds")
        @Expose
        val districts: List<String>
)