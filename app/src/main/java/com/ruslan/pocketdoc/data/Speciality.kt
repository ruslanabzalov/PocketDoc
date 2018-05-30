package com.ruslan.pocketdoc.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Speciality(
        @SerializedName("Id")
        @Expose
        val id: String,

        @SerializedName("Name")
        @Expose
        val name: String,

        @SerializedName("BranchName")
        @Expose
        val branchName: String
)