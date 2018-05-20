package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class City(
        @SerializedName("Id")
        @Expose
        val id: String,

        @SerializedName("Name")
        @Expose
        val name: String,

        @SerializedName("Latitude")
        @Expose
        val latitude: String,

        @SerializedName("Longitude")
        @Expose
        val longitude: String,

        @SerializedName("SearchType")
        @Expose
        val searchType: Int,

        @SerializedName("HasDiagnostic")
        @Expose
        val hasDiagnostic: Boolean,

        @SerializedName("TimeZone")
        @Expose
        val timeZone: Int
)