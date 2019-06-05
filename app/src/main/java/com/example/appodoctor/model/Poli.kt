package com.example.appodoctor.model

import com.google.gson.annotations.SerializedName

data class Poli (
    @SerializedName("id")
    val idPoli : String? = null,
    @SerializedName("nama_poli")
    val namaPoli: String? = null
)