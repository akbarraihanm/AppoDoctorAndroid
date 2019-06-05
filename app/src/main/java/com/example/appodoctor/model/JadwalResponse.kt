package com.example.appodoctor.model

import com.google.gson.annotations.SerializedName

data class JadwalResponse (
    @SerializedName("data")
    val data : ArrayList<JadwalModel>
)