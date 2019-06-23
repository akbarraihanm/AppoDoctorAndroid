package com.example.appodoctor.model

import com.google.gson.annotations.SerializedName

data class PasienResponse (
    @SerializedName("data")
    val data : ArrayList<Pasien>
)