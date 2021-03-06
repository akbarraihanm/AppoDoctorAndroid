package com.example.appodoctor.model

import com.google.gson.annotations.SerializedName

data class DokterModel (
    @SerializedName("id")
    var idDokter : String? = null,
    @SerializedName("nama_dokter")
    var namaDokter : String? = null,
    @SerializedName("notelp")
    var noTelp : String? = null,
    @SerializedName("poli_id")
    var poliId : String? = null,
    @SerializedName("nama_poli")
    var namaPoli : String? = null,
    @SerializedName("token_notif")
    var tokenDokter : String? = null
)