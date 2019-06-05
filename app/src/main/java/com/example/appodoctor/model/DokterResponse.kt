package com.example.appodoctor.model

import com.google.gson.annotations.SerializedName

data class DokterResponse (
    @SerializedName("data")
    val data : ArrayList<DokterModel>
)