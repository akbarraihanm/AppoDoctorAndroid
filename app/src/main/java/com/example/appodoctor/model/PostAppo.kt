package com.example.appodoctor.model

import com.google.gson.annotations.SerializedName

data class PostAppo (
    @field:SerializedName("tanggal")
    val tappo : String? = null,
    @field:SerializedName("pasien_id")
    val pid : String? = null,
    @field:SerializedName("dokter_id")
    val doid : String? = null,
    @field:SerializedName("poli_id")
    val poid : String? = null
)