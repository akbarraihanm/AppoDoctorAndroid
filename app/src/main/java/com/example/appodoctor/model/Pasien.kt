package com.example.appodoctor.model

import com.google.gson.annotations.SerializedName

data class Pasien (
    @SerializedName("id")
    val idPasien : String? = null,
    @SerializedName("nama_pasien")
    val namaPasien : String? = null,
    @SerializedName("alamat_pasien")
    val alamatPasien : String? = null,
    @SerializedName("norm_pasien")
    val normPasien : String? = null
)