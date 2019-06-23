package com.example.appodoctor.model

import com.google.gson.annotations.SerializedName

data class AppoResponse (
    @SerializedName("data")
    val data : ArrayList<Appointment>
)