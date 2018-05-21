package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Doctor(
        @SerializedName("Id")
        @Expose
        val id: Int,

        @SerializedName("Name")
        @Expose
        val name: String,

        @SerializedName("Rating")
        @Expose
        val rating: String,

        @SerializedName("Sex")
        @Expose
        val sex: Int,

        @SerializedName("Img")
        @Expose
        val photo: String,

        @SerializedName("Description")
        @Expose
        val description: String,

        @SerializedName("ExperienceYear")
        @Expose
        val experience: Int,

        @SerializedName("Price")
        @Expose
        val price: Int
)