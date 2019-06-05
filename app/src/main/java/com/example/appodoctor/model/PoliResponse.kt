package com.example.appodoctor.model

import com.google.gson.annotations.SerializedName

data class PoliResponse (
    @SerializedName("data")
    val data : ArrayList<Poli>
)